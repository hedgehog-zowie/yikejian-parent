package com.yikejian.user.api.v1;

import com.yikejian.user.api.v1.dto.RequestRoleDto;
import com.yikejian.user.api.v1.dto.RoleDto;
import com.yikejian.user.exception.UserServiceException;
import com.yikejian.user.service.RoleService;
import com.yikejian.user.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    @RequestMapping(value = "/role/{role_id}", method = RequestMethod.GET)
    public ResponseEntity getRole(final @PathVariable(value = "role_id") Long roleId) {
        // todo send log
        return Optional.ofNullable(roleService.getRoleById(roleId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @PostMapping("/role")
    public ResponseEntity addRole(@RequestBody final RoleDto roleDto) {
        roleDto.setRoleId(null);
        // todo send log
        return Optional.ofNullable(roleService.saveRole(roleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @PutMapping("/role")
    public ResponseEntity updateRole(@RequestBody final RoleDto roleDto) {
        if (roleDto.getRoleId() == null) {
            throw new UserServiceException("未知的角色");
        }
        // todo send log
        return Optional.ofNullable(roleService.saveRole(roleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @GetMapping("/roles")
    public ResponseEntity getRoles(final @RequestParam(value = "params") String params) {
        RequestRoleDto requestRoleDto;
        try {
            requestRoleDto = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestRoleDto.class);
        } catch (UnsupportedEncodingException e) {
            throw new UserServiceException(e.getLocalizedMessage());
        }
        // todo send log
        return Optional.ofNullable(roleService.getRoles(requestRoleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found any role."));
    }

    @GetMapping("/roles2")
    public ResponseEntity getRoles(@RequestBody final RequestRoleDto requestRoleDto) {
        // todo send log
        return Optional.ofNullable(roleService.getRoles(requestRoleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found any role."));
    }
}