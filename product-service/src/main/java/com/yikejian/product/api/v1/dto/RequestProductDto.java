package com.yikejian.product.api.v1.dto;

import com.yikejian.product.domain.product.Product;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestProductDto {

    private Product product;
    private Pagination pagination;
    private Sort sort;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
