package com.yikejian.gift.api.v1.dto;

import com.yikejian.gift.domain.gift.Gift;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseGift {

    private List<Gift> giftList;
    private Pagination pagination;

    public ResponseGift(List<Gift> giftList) {
        this.giftList = giftList;
    }

    public ResponseGift(List<Gift> giftList, Pagination pagination) {
        this.giftList = giftList;
        this.pagination = pagination;
    }

    public List<Gift> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<Gift> giftList) {
        this.giftList = giftList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
