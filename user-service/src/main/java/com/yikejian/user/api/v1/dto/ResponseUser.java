package com.yikejian.user.api.v1.dto;

import com.yikejian.user.domain.user.User;

import java.util.List;

/**
 * <code>ResponseUser</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseUser {

    private List<User> userList;
    private Pagination pagination;

    public ResponseUser() {
    }

    public ResponseUser(List<User> userList) {
        this.userList = userList;
    }

    public ResponseUser(List<User> userList, Pagination pagination) {
        this.userList = userList;
        this.pagination = pagination;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
