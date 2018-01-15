package com.yikejian.user.api.v1;

import com.yikejian.user.api.v1.dto.RequestRoleDto;
import com.yikejian.user.api.v1.dto.RoleDto;
import com.yikejian.user.exception.UserServiceException;
import com.yikejian.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/role/{role_id}", method = RequestMethod.GET)
    public ResponseEntity getRoles(final @PathVariable(value = "role_id") Long roleId) {
        // todo send log
        return Optional.ofNullable(roleService.getRoleById(roleId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public ResponseEntity addRole(final RoleDto roleDto) {
        // todo send log
        return Optional.ofNullable(roleService.saveRole(roleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    public ResponseEntity updateRole(final RoleDto roleDto) {
        // todo send log
        return Optional.ofNullable(roleService.saveRole(roleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found role."));
    }

    @GetMapping("/roles")
    public ResponseEntity getRoles(final RequestRoleDto requestRoleDto) {
        // todo send log
        return Optional.ofNullable(roleService.getRoles(requestRoleDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found any role."));
    }

}
