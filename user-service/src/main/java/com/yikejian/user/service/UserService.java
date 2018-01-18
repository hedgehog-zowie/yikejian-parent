package com.yikejian.user.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestUserDto;
import com.yikejian.user.api.v1.dto.ResponseUserDto;
import com.yikejian.user.api.v1.dto.UserDto;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.domain.user.User;
import com.yikejian.user.exception.UserServiceException;
import com.yikejian.user.repository.RoleRepository;
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
import java.util.Date;
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
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @HystrixCommand
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @HystrixCommand
    public User saveUser(UserDto userDto) {
        return userRepository.save(userDtoToUser(userDto));
    }

    @HystrixCommand
    public List<User> saveUsers(List<UserDto> userDtoList) {
        List<User> userList = Lists.newArrayList(userDtoList.stream().
                map(this::userDtoToUser).collect(Collectors.toList()));
        return (List<User>) userRepository.save(userList);
    }

    @HystrixCommand
    public UserDto getUserById(Long userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UserServiceException("未知的用户");
        }
        return userToUserDto(user);
    }

    @HystrixCommand
    public ResponseUserDto getAll() {
        List<User> userList = (List<User>) userRepository.findAll();
        List<UserDto> userDtoList = Lists.newArrayList(userList.stream().
                map(this::userToUserDto).collect(Collectors.toList()));
        return new ResponseUserDto(userDtoList);
    }

    @HystrixCommand
    public ResponseUserDto getUsers(RequestUserDto requestUserDto) {
        Pagination pagination;
        if (requestUserDto != null && requestUserDto.getPagination() != null) {
            pagination = requestUserDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestUserDto != null && requestUserDto.getSort() != null) {
            sort = new Sort(requestUserDto.getSort().getDirection(), requestUserDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<User> page = userRepository.findAll(userSpec(requestUserDto.getUser()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());

        List<UserDto> userDtoList = Lists.newArrayList(page.getContent().stream().
                map(this::userToUserDto).collect(Collectors.toList()));

        return new ResponseUserDto(userDtoList, pagination);
    }

    private Specification<User> userSpec(UserDto userDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (userDto != null) {
                if (userDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), userDto.getDeleted()));
                }
                if (userDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), userDto.getEffective()));
                }
                if (StringUtils.isNotBlank(userDto.getUserName())) {
                    predicateList.add(cb.like(root.get("userName").as(String.class), "%" + userDto.getUserName() + "%"));
                }
                if (userDto.getRoleId() != null) {
                    Join<User, Role> roleJoin = root.join(root.getModel().getSingularAttribute("role", Role.class), JoinType.LEFT);
                    predicateList.add(cb.equal(roleJoin.get("roleId").as(Long.class), userDto.getRoleId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    private User userDtoToUser(UserDto userDto) {
        User user;
        if (userDto.getUserId() != null) {
            user = userRepository.findByUserId(userDto.getUserId());
            if (user == null) {
                throw new UserServiceException("未知的用户");
            }
        } else {
            user = new User();
        }
        if (userDto.getRoleId() == null) {
            throw new UserServiceException("未设置用户角色");
        }
        Role role = roleRepository.findByRoleId(userDto.getRoleId());
        if (role == null) {
            throw new UserServiceException("未知的角色");
        }
        user.setRole(role);
        if (StringUtils.isNotBlank(userDto.getUserName())) {
            user.setUserName(userDto.getUserName());
        }
        if (userDto.getEffective() != null) {
            user.setEffective(userDto.getEffective());
        }
        if (userDto.getDeleted() != null) {
            user.setDeleted(userDto.getDeleted());
        }
        return user;
    }

    private UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUserName());
        userDto.setRoleId(user.getRole().getRoleId());
        userDto.setRoleName(user.getRole().getRoleName());
        userDto.setEffective(user.getEffective());
        userDto.setDeleted(user.getDeleted());
        userDto.setLastModifiedBy(user.getLastModifiedBy());
        userDto.setLastModifiedAt(user.getLastModifiedAt() == null ? null : new Date(user.getLastModifiedAt()));
        return userDto;
    }

}
