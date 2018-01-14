package com.yikejian.user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.user.domain.request.QueryUser;
import com.yikejian.user.domain.request.RequestUser;
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
    public Page<User> getUsers(QueryUser queryUser) {
        PageRequest pageRequest;
        if (queryUser.getSort() != null) {
            Sort sort = new Sort(queryUser.getSort().getDirection(), queryUser.getSort().getField());
            pageRequest = new PageRequest(
                    queryUser.getPagination().getCurrentPage(),
                    queryUser.getPagination().getPageSize(),
                    sort);
        } else {
            pageRequest = new PageRequest(
                    queryUser.getPagination().getCurrentPage(),
                    queryUser.getPagination().getPageSize());
        }
        Page<User> page = userRepository.findAll(userSpec(queryUser.getUser()), pageRequest);
        return page;
    }

    private Specification<User> userSpec(RequestUser requestUser) {
        return (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("deleted").as(Integer.class), 0);
            if (requestUser != null) {
                if (requestUser.getEffective() != null) {
                    predicate = cb.and(cb.equal(root.get("effective").as(Integer.class), requestUser.getEffective()));
                }
                if (StringUtils.isNotBlank(requestUser.getUserName())) {
                    predicate = cb.and(cb.like(root.get("userName").as(String.class), "%" + requestUser.getUserName() + "%"));
                }
                if (requestUser.getRoleId() != null) {
                    Join<User, Role> roleJoin = root.join(root.getModel().getSingularAttribute("roleId", Role.class), JoinType.LEFT);
                    predicate = cb.and(cb.equal(roleJoin.get("roleId").as(Long.class), requestUser.getRoleId()));
                }
            }
            return predicate;
        };
    }

}
