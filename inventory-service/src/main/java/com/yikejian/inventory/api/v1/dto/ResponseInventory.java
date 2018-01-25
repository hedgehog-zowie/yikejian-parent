package com.yikejian.inventory.api.v1.dto;

import com.yikejian.inventory.domain.inventory.Inventory;

import java.util.List;

/**
 * <code>ResponseRole</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseInventory {

    private List<Inventory> list;
    private Pagination pagination;

    public ResponseInventory() {
    }

    public ResponseInventory(List<Inventory> list) {
        this.list = list;
    }

    public ResponseInventory(List<Inventory> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<Inventory> getList() {
        return list;
    }

    public void setList(List<Inventory> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
