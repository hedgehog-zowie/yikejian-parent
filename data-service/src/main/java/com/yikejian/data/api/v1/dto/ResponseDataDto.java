package com.yikejian.data.api.v1.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseDataDto {

    private List<DataDto> dataList;
    private Pagination pagination;

    public ResponseDataDto(List<DataDto> dataList) {
        this.dataList = dataList;
    }

    public ResponseDataDto(List<DataDto> dataList, Pagination pagination) {
        this.dataList = dataList;
        this.pagination = pagination;
    }

    public List<DataDto> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataDto> dataList) {
        this.dataList = dataList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
