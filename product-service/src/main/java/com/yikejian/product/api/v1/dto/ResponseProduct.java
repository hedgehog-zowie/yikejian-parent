package com.yikejian.product.api.v1.dto;

import com.yikejian.product.domain.product.Product;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseProduct {

    private List<Product> productList;
    private Pagination pagination;

    public ResponseProduct(List<Product> productList) {
        this.productList = productList;
    }

    public ResponseProduct(List<Product> productList, Pagination pagination) {
        this.productList = productList;
        this.pagination = pagination;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
