package com.yikejian.user.api.v1;

import com.yikejian.user.api.v1.dto.RequestUserDto;
import com.yikejian.user.api.v1.dto.UserDto;
import com.yikejian.user.domain.user.User;
import com.yikejian.user.exception.UserServiceException;
import com.yikejian.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
    public ResponseEntity getUsers(final @PathVariable(value = "user_id") Long userId) {
        // todo send log
        return Optional.ofNullable(userService.getUserById(userId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found user."));
    }

    @PostMapping("/user")
    public ResponseEntity addUser(final UserDto userDto) {
        // todo send log
        return Optional.ofNullable(userService.saveUser(userDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found user."));
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(final UserDto userDto) {
        // todo send log
        return Optional.ofNullable(userService.saveUser(userDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found user."));
    }

    @GetMapping("/users")
    public ResponseEntity getUsers(final RequestUserDto requestUserDto) {
        // todo send log
        return Optional.ofNullable(userService.getUsers(requestUserDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found any user."));
    }

}
