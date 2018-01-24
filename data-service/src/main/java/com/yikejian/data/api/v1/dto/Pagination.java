package com.yikejian.data.api.v1.dto;

/**
 * @author jackalope
 * @Title: Pagination
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 18:00
 */
public class Pagination {

    private static final Integer DEFAULT_CURRENT = 1;
    private static final Integer DEFAULT_PAGESIZE = 100;

    private Integer current;
    private Integer pageSize;
    private Integer totalPages;
    private Long total;

    public Pagination() {
        this.current = DEFAULT_CURRENT;
        this.pageSize = DEFAULT_PAGESIZE;
    }

    public Pagination(Integer current, Integer pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }

    public Pagination(Integer current, Integer pageSize, Integer totalPages, Long total) {
        this.current = current;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.total = total;
    }

    @Deprecated
    public void plusCurrent(){
        this.current++;
    }

    @Deprecated
    public void minusCurrent(){
        this.current = this.current == 0 ? 0 : --this.current;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
