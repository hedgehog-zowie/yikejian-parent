package com.yikejian.customer.domain.code;

/**
 * @author jackalope
 * @Title: Code
 * @Package com.yikejian.customer.domain.code
 * @Description: TODO
 * @date 2018/2/6 21:58
 */
public class Code {

    private String code;
    private Integer timestamp;

    public Code() {
    }

    public Code(String code, Integer timestamp) {
        this.code = code;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

}
