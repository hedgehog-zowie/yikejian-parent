package com.yikejian.gift.api.vi.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseGiftDto {

    private List<GiftDto> giftList;
    private Pagination pagination;

    public ResponseGiftDto(List<GiftDto> giftList) {
        this.giftList = giftList;
    }

    public ResponseGiftDto(List<GiftDto> giftList, Pagination pagination) {
        this.giftList = giftList;
        this.pagination = pagination;
    }

    public List<GiftDto> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<GiftDto> giftList) {
        this.giftList = giftList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
