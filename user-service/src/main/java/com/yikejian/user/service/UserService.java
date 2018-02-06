package com.yikejian.user.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestUser;
import com.yikejian.user.api.v1.dto.ResponseUser;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.domain.user.User;
import com.yikejian.user.domain.user.UserType;
import com.yikejian.user.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return userRepository.findByName(username);
    }

    @HystrixCommand
    public User saveUser(User user) {
        if (UserType.CUSTOMER.equals(user.getUserType())) {
            User savedUser = userRepository.findByName(user.getName());
            if (savedUser != null) {
                return savedUser;
            }
            user.setEffective(1);
            user.setDeleted(0);
            Role role = new Role(6L);
            user.setRole(role);
        }
        return userRepository.save(transform(user));
    }

    @HystrixCommand
    public List<User> saveUsers(List<User> userList) {
        return (List<User>) userRepository.save(
                Lists.newArrayList(userList.stream().
                        map(this::transform).collect(Collectors.toList()))
        );
    }

    private User transform(User user) {
        User newUser = user;
        if (user.getId() != null) {
            User oldUser = userRepository.findById(user.getId());
            newUser = oldUser.mergeOther(user);
        }
        return newUser;
    }

    @HystrixCommand
    public User getUserById(Long userId) {
        return userRepository.findById(userId);
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

        String filed = "lastModifiedAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (requestUser != null && requestUser.getSorter() != null) {
            if (requestUser.getSorter().getField() != null) {
                filed = requestUser.getSorter().getField();
            }
            if ("ascend".equals(requestUser.getSorter().getOrder())) {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sort = new Sort(direction, filed);

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent() - 1,
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
                if (StringUtils.isNotBlank(user.getName())) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%" + user.getName() + "%"));
                }
                if (user.getRole() != null && user.getRole().getRoleId() != null) {
//                    Join<User, Role> join = root.join(root.getModel().getSingularAttribute("role", Role.class), JoinType.LEFT);
//                    predicateList.add(cb.equal(join.get("roleId").as(Long.class), user.getRole().getRoleId()));
                    Join<User, Role> join = root.join("role");
                    predicateList.add(cb.equal(join.<Long>get("roleId"), user.getRole().getRoleId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
