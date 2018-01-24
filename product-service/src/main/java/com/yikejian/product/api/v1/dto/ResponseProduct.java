package com.yikejian.product.api.v1.dto;

import com.yikejian.product.domain.product.Product;
import com.yikejian.user.api.v1.dto.Pagination;

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

    private List<Product> list;
    private Pagination pagination;

    public ResponseProduct(List<Product> list) {
        this.list = list;
    }

    public ResponseProduct(List<Product> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
