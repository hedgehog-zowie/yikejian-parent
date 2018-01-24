package com.yikejian.user.api.v1.dto;

/**
 * @author jackalope
 * @Title: AuthorityDto
 * @Package com.yikejian.customer.api.v1.dto
 * @Description: TODO
 * @date 2018/1/21 14:57
 */
public class AuthorityDto {

    private String code;
    private String name;

    public AuthorityDto(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
