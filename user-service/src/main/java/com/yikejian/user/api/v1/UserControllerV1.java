package com.yikejian.user.api.v1;

import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestUser;
import com.yikejian.user.domain.user.User;
import com.yikejian.user.exception.UserServiceException;
import com.yikejian.user.service.UserService;
import com.yikejian.user.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
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
public class UserControllerV1 {

    private UserService userService;

    @Autowired
    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody final User user) {
        user.setId(null);
        // todo send log
        return Optional.ofNullable(userService.saveUser(user))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found user."));
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestBody final User user) {
        // todo send log
        return Optional.ofNullable(userService.saveUser(user))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found user."));
    }

    @PutMapping("/users")
    public ResponseEntity updateUsers(@RequestBody final List<User> userList) {
        // todo send log
        return Optional.ofNullable(userService.saveUsers(userList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found user."));
    }

    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
    public ResponseEntity getUser(final @PathVariable(value = "user_id") Long userId) {
        // todo send log
        return Optional.ofNullable(userService.getUserById(userId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found user."));
    }

    @GetMapping("/users")
    public ResponseEntity getUsers(final @RequestParam(value = "params", required = false) String params) {
        RequestUser requestUser = new RequestUser();
        if (StringUtils.isNotBlank(params)) {
            try {
                requestUser = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestUser.class);
            } catch (UnsupportedEncodingException e) {
                throw new UserServiceException(e.getLocalizedMessage());
            }
        }
        if (requestUser.getUser() == null) {
            requestUser.setUser(new User());
        }
        if (requestUser.getPagination() == null) {
            requestUser.setPagination(new Pagination());
        }
        requestUser.getUser().setDeleted(0);
        // todo send log
        return Optional.ofNullable(userService.getUsers(requestUser))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found any user."));
    }

    @GetMapping("/me")
    public ResponseEntity me(Principal principal) {
        User user = null;
        if (principal != null) {
            user = userService.getUserByUsername(principal.getName());
        }

        return Optional.ofNullable(user)
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

}
