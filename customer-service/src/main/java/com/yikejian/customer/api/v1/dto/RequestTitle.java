package com.yikejian.customer.api.v1.dto;

import com.yikejian.customer.domain.title.Title;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestTitle {

    private Title title;
    private Pagination pagination;
    private Sort sorter;

    public RequestTitle(Title title, Pagination pagination, Sort sorter) {
        this.title = title;
        this.pagination = pagination;
        this.sorter = sorter;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sort getSorter() {
        return sorter;
    }

    public void setSorter(Sort sorter) {
        this.sorter = sorter;
    }
}
