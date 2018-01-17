package com.yikejian.inventory.api.v1.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseInventoryDto {

    private List<InventoryDto> inventoryList;
    private Pagination pagination;

    public ResponseInventoryDto(List<InventoryDto> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public ResponseInventoryDto(List<InventoryDto> inventoryList, Pagination pagination) {
        this.inventoryList = inventoryList;
        this.pagination = pagination;
    }

    public List<InventoryDto> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<InventoryDto> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
