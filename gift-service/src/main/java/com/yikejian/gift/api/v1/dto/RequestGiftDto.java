package com.yikejian.gift.api.vi.dto;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestGiftDto {

    private GiftDto gift;
    private Pagination pagination;
    private Sort sort;

    public GiftDto getGift() {
        return gift;
    }

    public void setGift(GiftDto gift) {
        this.gift = gift;
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
