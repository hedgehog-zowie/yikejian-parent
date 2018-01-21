package com.yikejian.user.api.v1;

import com.yikejian.user.domain.role.Authority;
import com.yikejian.user.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author jackalope
 * @Title: AuthorityControllerV1
 * @Package com.yikejian.user.api.v1
 * @Description: TODO
 * @date 2018/1/21 14:12
 */
@RestController
@RequestMapping(path = "/v1")
public class AuthorityControllerV1 {

    @GetMapping("/authorities")
    public ResponseEntity getAuthorities() {
        // todo send log
        return Optional.ofNullable(Authority.getAll())
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UserServiceException("Not found any authority."));
    }

}
