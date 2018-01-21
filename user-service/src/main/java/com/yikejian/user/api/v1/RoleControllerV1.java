package com.yikejian.user.api.v1;

import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestRole;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.exception.UserServiceException;
import com.yikejian.user.service.RoleService;
import com.yikejian.user.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

/**
 * @author jackalope
 * @Title: UserController
 * @Package com.yikejian.user.api.v1
 * @Description: TODO
 * @date 2018/1/14 10:35
 */
@RestController
@RequestMapping(path = "/v1")
public class RoleControllerV1 {

    private RoleService roleService;

    @Autowired
    public RoleControllerV1(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role")
    public ResponseEntity addRole(@RequestBody final Role role) {
        role.setRoleId(null);
        // todo send log
        return Optional.ofNullable(roleService.saveRole(role))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @PutMapping("/role")
    public ResponseEntity updateRole(@RequestBody final Role role) {
        // todo send log
        return Optional.ofNullable(roleService.saveRole(role))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @PutMapping("/roles")
    public ResponseEntity updateRoles(@RequestBody final List<Role> roleList) {
        // todo send log
        return Optional.ofNullable(roleService.saveRoles(roleList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @RequestMapping(value = "/role/{role_id}", method = RequestMethod.GET)
    public ResponseEntity getRole(final @PathVariable(value = "role_id") Long roleId) {
        // todo send log
        return Optional.ofNullable(roleService.getRoleById(roleId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @GetMapping("/roles")
    public ResponseEntity getRoles(final @RequestParam(value = "params", required = false) String params) {
        RequestRole requestRole;
        if (StringUtils.isBlank(params)) {
            requestRole = new RequestRole(new Role(), new Pagination(), null);
        } else {
            try {
                requestRole = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestRole.class);
            } catch (UnsupportedEncodingException e) {
                throw new UserServiceException(e.getLocalizedMessage());
            }
        }
        requestRole.getRole().setDeleted(0);
        // todo send log
        return Optional.ofNullable(roleService.getRoles(requestRole))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found any role."));
    }

    // test for get roles using RequestRole object
    @GetMapping("/roles2")
    public ResponseEntity getRoles(@RequestBody final RequestRole requestRole) {
        // todo send log
        return Optional.ofNullable(roleService.getRoles(requestRole))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found any role."));
    }
}