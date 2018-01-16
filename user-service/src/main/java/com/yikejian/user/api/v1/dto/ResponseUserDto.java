package com.yikejian.user.api.v1.dto;

import java.util.List;

/**
 * <code>ResponseUserDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseUserDto {

    private List<UserDto> userList;
    private Pagination pagination;

    public ResponseUserDto() {
    }

    public ResponseUserDto(List<UserDto> userList) {
        this.userList = userList;
    }

    public ResponseUserDto(List<UserDto> userList, Pagination pagination) {
        this.userList = userList;
        this.pagination = pagination;
    }

    public List<UserDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDto> userList) {
        this.userList = userList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
