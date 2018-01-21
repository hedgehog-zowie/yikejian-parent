package com.yikejian.user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestUser;
import com.yikejian.user.api.v1.dto.ResponseUser;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.domain.user.User;
import com.yikejian.user.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jackalope
 * @Title: UserService
 * @Package com.yikejian.user.service
 * @Description: TODO
 * @date 2018/1/14 10:36
 */
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @HystrixCommand
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @HystrixCommand
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @HystrixCommand
    public List<User> saveUsers(List<User> userList) {
        return (List<User>) userRepository.save(userList);
    }

    @HystrixCommand
    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @HystrixCommand
    public ResponseUser getAll() {
        return new ResponseUser((List<User>) userRepository.findAll());
    }

    @HystrixCommand
    public ResponseUser getUsers(RequestUser requestUser) {
        Pagination pagination;
        if (requestUser != null && requestUser.getPagination() != null) {
            pagination = requestUser.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestUser != null && requestUser.getSort() != null) {
            sort = new Sort(requestUser.getSort().getOrder(), requestUser.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent(),
                pagination.getPageSize(),
                sort);
        Page<User> page = userRepository.findAll(userSpec(requestUser.getUser()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

        return new ResponseUser(page.getContent(), pagination);
    }

    private Specification<User> userSpec(User user) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (user != null) {
                if (user.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), user.getDeleted()));
                }
                if (user.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), user.getEffective()));
                }
                if (StringUtils.isNotBlank(user.getUserName())) {
                    predicateList.add(cb.like(root.get("userName").as(String.class), "%" + user.getUserName() + "%"));
                }
                if (user.getRole() != null && user.getRole().getRoleId() != null) {
                    Join<User, Role> roleJoin = root.join(root.getModel().getSingularAttribute("role", Role.class), JoinType.LEFT);
                    predicateList.add(cb.equal(roleJoin.get("roleId").as(Long.class), user.getRole().getRoleId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
