package com.yikejian.inventory.api.v1.dto;

import com.yikejian.inventory.domain.inventory.Inventory;

/**
 * @author jackalope
 * @Title: QueryInventory
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestInventory {

    private Inventory inventory;
    private Pagination pagination;
    private Sort sorter;

    public RequestInventory() {
    }

    public RequestInventory(Inventory inventory, Pagination pagination, Sort sorter) {
        this.inventory = inventory;
        this.pagination = pagination;
        this.sorter = sorter;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
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
