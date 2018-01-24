package com.yikejian.product.api.v1.dto;

import com.yikejian.product.domain.product.Product;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.Sort;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestProduct {

    private Product product;
    private Pagination pagination;
    private Sort sorter;

    public RequestProduct(Product product, Pagination pagination, Sort sorter) {
        this.product = product;
        this.pagination = pagination;
        this.sorter = sorter;
    }

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

    public Sort getSorter() {
        return sorter;
    }

    public void setSorter(Sort sorter) {
        this.sorter = sorter;
    }
}
