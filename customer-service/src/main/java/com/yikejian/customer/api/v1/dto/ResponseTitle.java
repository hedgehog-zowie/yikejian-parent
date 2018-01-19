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

    private List<Title> titleList;
    private Pagination pagination;

    public ResponseTitle(List<Title> titleList) {
        this.titleList = titleList;
    }

    public ResponseTitle(List<Title> titleList, Pagination pagination) {
        this.titleList = titleList;
        this.pagination = pagination;
    }

    public List<Title> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<Title> titleList) {
        this.titleList = titleList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
