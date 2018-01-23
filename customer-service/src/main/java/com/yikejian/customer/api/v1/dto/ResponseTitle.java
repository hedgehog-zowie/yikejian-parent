package com.yikejian.customer.api.v1.dto;

import com.yikejian.customer.domain.title.Title;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseTitle {

    private List<Title> list;
    private Pagination pagination;

    public ResponseTitle(List<Title> list) {
        this.list = list;
    }

    public ResponseTitle(List<Title> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<Title> getList() {
        return list;
    }

    public void setList(List<Title> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
