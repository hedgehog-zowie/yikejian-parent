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

    private List<User> list;
    private Pagination pagination;

    public ResponseUser() {
    }

    public ResponseUser(List<User> list) {
        this.list = list;
    }

    public ResponseUser(List<User> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
