package com.yikejian.user.repository;

import com.yikejian.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>UserRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/19 16:50
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
