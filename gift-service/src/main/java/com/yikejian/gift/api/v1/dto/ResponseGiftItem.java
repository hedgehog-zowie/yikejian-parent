package com.yikejian.gift.api.v1.dto;

import com.yikejian.gift.domain.item.GiftItem;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseGiftItem {

    private List<GiftItem> giftItemList;
    private Pagination pagination;

    public ResponseGiftItem(List<GiftItem> giftItemList) {
        this.giftItemList = giftItemList;
    }

    public ResponseGiftItem(List<GiftItem> giftItemList, Pagination pagination) {
        this.giftItemList = giftItemList;
        this.pagination = pagination;
    }

    public List<GiftItem> getGiftItemList() {
        return giftItemList;
    }

    public void setGiftItemList(List<GiftItem> giftItemList) {
        this.giftItemList = giftItemList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
