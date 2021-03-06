package com.yikejian.gift.api.v1.dto;

import com.yikejian.gift.domain.item.GiftItem;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestGiftItem {

    private GiftItem giftItem;
    private Pagination pagination;
    private Sort sort;

    public GiftItem getGiftItem() {
        return giftItem;
    }

    public void setGiftItem(GiftItem giftItem) {
        this.giftItem = giftItem;
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
