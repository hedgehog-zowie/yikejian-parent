package com.yikejian.user.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestRoleDto;
import com.yikejian.user.api.v1.dto.ResponseRoleDto;
import com.yikejian.user.api.v1.dto.RoleDto;
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
    public Role saveRole(RoleDto roleDto) {
        Role role;
        if (roleDto.getRoleId() != null) {
            role = roleRepository.findByRoleId(roleDto.getRoleId());
        } else {
            role = new Role();
        }
        role.fromRoleDto(roleDto);
        return roleRepository.save(role);
    }

    @HystrixCommand
    public List<Role> saveRoles(List<RoleDto> roleDtoList) {
        List<Role> roleList = Lists.newArrayList();
        for (RoleDto roleDto : roleDtoList) {
            Role role;
            if (roleDto.getRoleId() != null) {
                role = roleRepository.findByRoleId(roleDto.getRoleId());
            } else {
                role = new Role();
            }
            role.fromRoleDto(roleDto);
            roleList.add(role);
        }
        return (List<Role>) roleRepository.save(roleList);
    }

    @HystrixCommand
    public RoleDto getRoleById(Long roleId) {
        Role role = roleRepository.findByRoleId(roleId);
        return role.toRoleDto();
    }

    @HystrixCommand
    public ResponseRoleDto getAll() {
        List<Role> roleList = (List<Role>) roleRepository.findAll();
        List<RoleDto> roleDtoList = Lists.newArrayList(
                roleList.stream().map(Role::toRoleDto).collect(Collectors.toList())
        );
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
                map(Role::toRoleDto).collect(Collectors.toList()));
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

}
