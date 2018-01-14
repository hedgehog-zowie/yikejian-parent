package com.yikejian.user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.user.domain.request.QueryRole;
import com.yikejian.user.domain.request.RequestRole;
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
    public RoleService(RoleRepository RoleRepository) {
        this.roleRepository = RoleRepository;
    }

    @HystrixCommand
    public Page<Role> getRoles(QueryRole queryRole) {
        PageRequest pageRequest;
        if (queryRole.getSort() != null) {
            Sort sort = new Sort(queryRole.getSort().getDirection(), queryRole.getSort().getField());
            pageRequest = new PageRequest(
                    queryRole.getPagination().getCurrentPage(),
                    queryRole.getPagination().getPageSize(),
                    sort);
        } else {
            pageRequest = new PageRequest(
                    queryRole.getPagination().getCurrentPage(),
                    queryRole.getPagination().getPageSize());
        }
        Page<Role> page = roleRepository.findAll(RoleSpec(queryRole.getRole()), pageRequest);
        return page;
    }

    private Specification<Role> RoleSpec(RequestRole requestRole) {
        return (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("deleted").as(Integer.class), 0);
            if (requestRole != null) {
                if (requestRole.getEffective() != null) {
                    predicate = cb.and(cb.equal(root.get("effective").as(Integer.class), requestRole.getEffective()));
                }
                if (StringUtils.isNotBlank(requestRole.getRoleName())) {
                    predicate = cb.and(cb.like(root.get("roleName").as(String.class), "%" + requestRole.getRoleName() + "%"));
                }
                if (requestRole.getAuthority() != null) {
                    predicate = cb.and(cb.like(root.get("authorities").as(String.class), "%" + requestRole.getAuthority() + "%"));
                }
            }
            return predicate;
        };
    }

}
