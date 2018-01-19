package com.yikejian.message.service;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * <code>ResponseCode</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/19 17:49
 */
public enum ResponseCode {
    OK("OK", "请求成功"),
    RAM_PERMISSION_DENY("isp.RAM_PERMISSION_DENY", "RAM权限DENY"),
    OUT_OF_SERVICE("isv.OUT_OF_SERVICE", "业务停机"),
    PRODUCT_UN_SUBSCRIPT("isv.PRODUCT_UN_SUBSCRIPT", "未开通云通信产品的阿里云客户"),
    PRODUCT_UNSUBSCRIBE("isv.PRODUCT_UNSUBSCRIBE", "产品未开通"),
    ACCOUNT_NOT_EXISTS("isv.ACCOUNT_NOT_EXISTS", "账户不存在"),
    ACCOUNT_ABNORMAL("isv.ACCOUNT_ABNORMAL", "账户异常"),
    SMS_TEMPLATE_ILLEGAL("isv.SMS_TEMPLATE_ILLEGAL", "短信模板不合法"),
    SMS_SIGNATURE_ILLEGAL("isv.SMS_SIGNATURE_ILLEGAL", "短信签名不合法"),
    INVALID_PARAMETERS("isv.INVALID_PARAMETERS", "参数异常"),
    SYSTEM_ERROR("isp.SYSTEM_ERROR", "系统错误"),
    MOBILE_NUMBER_ILLEGAL("isv.MOBILE_NUMBER_ILLEGAL", "非法手机号"),
    MOBILE_COUNT_OVER_LIMIT("isv.MOBILE_COUNT_OVER_LIMIT", "手机号码数量超过限制"),
    TEMPLATE_MISSING_PARAMETERS("isv.TEMPLATE_MISSING_PARAMETERS", "模板缺少变量"),
    BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL", "业务限流"),
    INVALID_JSON_PARAM("isv.INVALID_JSON_PARAM", "JSON参数不合法，只接受字符串值"),
    BLACK_KEY_CONTROL_LIMIT("isv.BLACK_KEY_CONTROL_LIMIT", "黑名单管控"),
    PARAM_LENGTH_LIMIT("isv.PARAM_LENGTH_LIMIT", "参数超出长度限制"),
    PARAM_NOT_SUPPORT_URL("isv.PARAM_NOT_SUPPORT_URL", "不支持URL"),
    AMOUNT_NOT_ENOUGH("isv.AMOUNT_NOT_ENOUGH", "账户余额不足"),;

    private String code;
    private String desc;

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, String> getResponseCodeMap(){
        Map<String, String> map = Maps.newHashMap();
        map.put(OK.getCode(), OK.getDesc());
        map.put(RAM_PERMISSION_DENY.getCode(), RAM_PERMISSION_DENY.getDesc());
        map.put(OUT_OF_SERVICE.getCode(), OUT_OF_SERVICE.getDesc());
        map.put(PRODUCT_UN_SUBSCRIPT.getCode(), PRODUCT_UN_SUBSCRIPT.getDesc());
        map.put(PRODUCT_UNSUBSCRIBE.getCode(), PRODUCT_UNSUBSCRIBE.getDesc());
        map.put(ACCOUNT_NOT_EXISTS.getCode(), ACCOUNT_NOT_EXISTS.getDesc());
        map.put(ACCOUNT_ABNORMAL.getCode(), ACCOUNT_ABNORMAL.getDesc());
        map.put(SMS_TEMPLATE_ILLEGAL.getCode(), SMS_TEMPLATE_ILLEGAL.getDesc());
        map.put(SMS_SIGNATURE_ILLEGAL.getCode(), SMS_SIGNATURE_ILLEGAL.getDesc());
        map.put(INVALID_PARAMETERS.getCode(), INVALID_PARAMETERS.getDesc());
        map.put(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getDesc());
        map.put(MOBILE_NUMBER_ILLEGAL.getCode(), MOBILE_NUMBER_ILLEGAL.getDesc());
        map.put(MOBILE_COUNT_OVER_LIMIT.getCode(), MOBILE_COUNT_OVER_LIMIT.getDesc());
        map.put(TEMPLATE_MISSING_PARAMETERS.getCode(), TEMPLATE_MISSING_PARAMETERS.getDesc());
        map.put(BUSINESS_LIMIT_CONTROL.getCode(), BUSINESS_LIMIT_CONTROL.getDesc());
        map.put(INVALID_JSON_PARAM.getCode(), INVALID_JSON_PARAM.getDesc());
        map.put(BLACK_KEY_CONTROL_LIMIT.getCode(), BLACK_KEY_CONTROL_LIMIT.getDesc());
        map.put(PARAM_LENGTH_LIMIT.getCode(), PARAM_LENGTH_LIMIT.getDesc());
        map.put(PARAM_NOT_SUPPORT_URL.getCode(), PARAM_NOT_SUPPORT_URL.getDesc());
        map.put(AMOUNT_NOT_ENOUGH.getCode(), AMOUNT_NOT_ENOUGH.getDesc());
        return map;
    }

}
