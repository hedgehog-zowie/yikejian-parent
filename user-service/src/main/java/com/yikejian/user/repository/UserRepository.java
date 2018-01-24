package com.yikejian.user.repository;

import com.yikejian.user.domain.role.Role;
import com.yikejian.user.domain.user.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <code>UserRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/19 16:50
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findById(Long userId);

    User findByName(String name);

    User findByEffectiveAndDeleted(Integer effective, Integer deleted);

    User findByNameAndEffectiveAndDeleted(String name, Integer effective, Integer deleted);

    List<User> findByRole(Role role);

}
