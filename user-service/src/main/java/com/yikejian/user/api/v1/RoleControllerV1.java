package com.yikejian.user.api.v1;

import com.yikejian.user.api.v1.dto.RequestRoleDto;
import com.yikejian.user.api.v1.dto.RoleDto;
import com.yikejian.user.exception.UserServiceException;
import com.yikejian.user.service.RoleService;
import com.yikejian.user.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity addRole(final RoleDto roleDto) {
        // todo send log
        return Optional.ofNullable(roleService.saveRole(roleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @PutMapping("/role")
    public ResponseEntity updateRole(final RoleDto roleDto) {
        // todo send log
        return Optional.ofNullable(roleService.saveRole(roleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
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

}
