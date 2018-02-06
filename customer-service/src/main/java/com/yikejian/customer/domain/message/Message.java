package com.yikejian.customer.domain.message;

/**
 * <code>Message</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Message {

    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 验证码
     */
    private String code;
    /**
     * 短信内容
     */
    private String content;

    public Message() {
    }

    public Message(String phoneNumber, String code, String content) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.content = content;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}