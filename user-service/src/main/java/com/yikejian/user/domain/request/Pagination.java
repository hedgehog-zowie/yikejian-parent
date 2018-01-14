package com.yikejian.user.domain.request;

/**
 * @author jackalope
 * @Title: Pagination
 * @Package com.yikejian.user.domain.request
 * @Description: TODO
 * @date 2018/1/14 18:00
 */
public class Pagination {

    private Integer currentPage;
    private Integer pageSize;

    public Pagination(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
