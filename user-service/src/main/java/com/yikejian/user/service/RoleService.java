package com.yikejian.user.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestRole;
import com.yikejian.user.api.v1.dto.ResponseRole;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.repository.RoleRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jackalope
 * @Title: RoleService
 * @Package com.yikejian.Role.service
 * @Description: TODO
 * @date 2018/1/14 10:36
 */
@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @HystrixCommand
    public Role saveRole(Role role) {
        return roleRepository.save(transRole(role));
    }

    @HystrixCommand
    public List<Role> saveRoles(List<Role> roleList) {
        return (List<Role>) roleRepository.save(
                Lists.newArrayList(roleList.stream().
                map(this::transRole).collect(Collectors.toList()))
        );
    }

    private Role transRole(Role role) {
        Role newRole = role;
        if (role.getRoleId() != null) {
            Role oldRole = roleRepository.findByRoleId(role.getRoleId());
            newRole = oldRole.mergeOtherRole(role);
        }
        return newRole;
    }

    @HystrixCommand
    public Role getRoleById(Long roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    @HystrixCommand
    public ResponseRole getAll() {
        return new ResponseRole((List<Role>) roleRepository.findAll());
    }

    @HystrixCommand
    public ResponseRole getRoles(RequestRole requestRole) {
        Pagination pagination;
        if (requestRole != null && requestRole.getPagination() != null) {
            pagination = requestRole.getPagination();
        } else {
            pagination = new Pagination();
        }

        String filed = "lastModifiedAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (requestRole != null && requestRole.getSorter() != null) {
            if (requestRole.getSorter().getField() != null) {
                filed = requestRole.getSorter().getField();
            }
            if ("ascend".equals(requestRole.getSorter().getOrder())) {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sort = new Sort(direction, filed);

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent() - 1,
                pagination.getPageSize(),
                sort);
        Page<Role> page = roleRepository.findAll(roleSpec(requestRole.getRole()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());
        List<Role> roleList = page.getContent();
        return new ResponseRole(roleList, pagination);
    }

    private Specification<Role> roleSpec(final Role role) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (role != null) {
                if (role.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), role.getDeleted()));
                }
                if (role.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), role.getEffective()));
                }
                if (StringUtils.isNotBlank(role.getRoleName())) {
                    predicateList.add(cb.like(root.get("roleName").as(String.class), "%" + role.getRoleName() + "%"));
                }
                if (role.getAuthorities() != null) {
                    predicateList.add(cb.like(root.get("authorities").as(String.class), "%" + role.getAuthorities() + "%"));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
