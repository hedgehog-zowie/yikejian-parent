package com.yikejian.inventory.api.v1.dto;

import java.util.List;

/**
 * @author jackalope
 * @Title: InventoryItemsDto
 * @Package com.yikejian.inventory.api.v1.dto
 * @Description: TODO
 * @date 2018/2/7 23:37
 */
public class InventoryItemsDto {

    private String pieceTime;
    private Integer bookedStock;
    private Integer restStock;
    private List<OrderItemDto> items;

    public String getPieceTime() {
        return pieceTime;
    }

    public void setPieceTime(String pieceTime) {
        this.pieceTime = pieceTime;
    }

    public Integer getBookedStock() {
        return bookedStock;
    }

    public void setBookedStock(Integer bookedStock) {
        this.bookedStock = bookedStock;
    }

    public Integer getRestStock() {
        return restStock;
    }

    public void setRestStock(Integer restStock) {
        this.restStock = restStock;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
