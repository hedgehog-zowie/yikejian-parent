package com.yikejian.payment.api.v1.dto;

/**
 * @author jackalope
 * @Title: Pagination
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 18:00
 */
public class Pagination {

    private static final Integer DEFAULT_CURRENT = 0;
    private static final Integer DEFAULT_PAGESIZE = 10;

    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalSize;

    public Pagination() {
        this.currentPage = DEFAULT_CURRENT;
        this.pageSize = DEFAULT_PAGESIZE;
    }

    public Pagination(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Pagination(Integer currentPage, Integer pageSize, Integer totalPages, Long totalSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalSize = totalSize;
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

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }
}
