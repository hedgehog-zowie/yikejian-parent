package com.yikejian.product.api.vi.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseProductDto {

    private List<ProductDto> productList;
    private Pagination pagination;

    public ResponseProductDto(List<ProductDto> productList) {
        this.productList = productList;
    }

    public ResponseProductDto(List<ProductDto> productList, Pagination pagination) {
        this.productList = productList;
        this.pagination = pagination;
    }

    public List<ProductDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDto> productList) {
        this.productList = productList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
