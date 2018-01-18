package com.yikejian.user.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestRoleDto;
import com.yikejian.user.api.v1.dto.ResponseRoleDto;
import com.yikejian.user.api.v1.dto.RoleDto;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.exception.UserServiceException;
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
import java.util.Date;
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
    public Role saveRole(RoleDto roleDto) {
        return roleRepository.save(roleDtoToRole(roleDto));
    }

    @HystrixCommand
    public List<Role> saveRoles(List<RoleDto> roleDtoList) {
        List<Role> roleList = Lists.newArrayList(roleDtoList.stream().
                map(this::roleDtoToRole).collect(Collectors.toList()));
        return (List<Role>) roleRepository.save(roleList);
    }

    @HystrixCommand
    public RoleDto getRoleById(Long roleId) {
        Role role = roleRepository.findByRoleId(roleId);
        if (role == null) {
            throw new UserServiceException("未知的角色");
        }
        return roleToRoleDto(role);
    }

    @HystrixCommand
    public ResponseRoleDto getAll() {
        List<Role> roleList = (List<Role>) roleRepository.findAll();
        List<RoleDto> roleDtoList = Lists.newArrayList(roleList.stream().
                map(this::roleToRoleDto).collect(Collectors.toList()));
        return new ResponseRoleDto(roleDtoList);
    }

    @HystrixCommand
    public ResponseRoleDto getRoles(RequestRoleDto requestRoleDto) {
        Pagination pagination;
        if (requestRoleDto != null && requestRoleDto.getPagination() != null) {
            pagination = requestRoleDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestRoleDto != null && requestRoleDto.getSort() != null) {
            sort = new Sort(requestRoleDto.getSort().getDirection(), requestRoleDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Role> page = roleRepository.findAll(roleSpec(requestRoleDto.getRole()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<RoleDto> roleDtoList = Lists.newArrayList(page.getContent().stream().
                map(this::roleToRoleDto).collect(Collectors.toList()));
        return new ResponseRoleDto(roleDtoList, pagination);
    }

    private Specification<Role> roleSpec(final RoleDto roleDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (roleDto != null) {
                if (roleDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), roleDto.getDeleted()));
                }
                if (roleDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), roleDto.getEffective()));
                }
                if (StringUtils.isNotBlank(roleDto.getRoleName())) {
                    predicateList.add(cb.like(root.get("roleName").as(String.class), "%" + roleDto.getRoleName() + "%"));
                }
                if (roleDto.getAuthorities() != null) {
                    predicateList.add(cb.like(root.get("authorities").as(String.class), "%" + roleDto.getAuthorities() + "%"));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    private Role roleDtoToRole(RoleDto roleDto) {
        Role role;
        if (roleDto.getRoleId() != null) {
            role = roleRepository.findByRoleId(roleDto.getRoleId());
            if (role == null) {
                throw new UserServiceException("未知的角色");
            }
        } else {
            role = new Role();
        }
        if (StringUtils.isNotBlank(roleDto.getRoleName())) {
            role.setRoleName(roleDto.getRoleName());
        }
        if (StringUtils.isNotBlank(roleDto.getAuthorities())) {
            role.setAuthorities(roleDto.getAuthorities());
        }
        if (roleDto.getEffective() != null) {
            role.setEffective(roleDto.getEffective());
        }
        if (roleDto.getDeleted() != null) {
            role.setDeleted(roleDto.getDeleted());
        }
        return role;
    }

    private RoleDto roleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setAuthorities(role.getAuthorities());
        roleDto.setEffective(role.getEffective());
        roleDto.setDeleted(role.getDeleted());
        roleDto.setLastModifiedBy(role.getLastModifiedBy());
        roleDto.setLastModifiedAt(role.getLastModifiedAt() == null ? null : new Date(role.getLastModifiedAt()));
        return roleDto;
    }

}
