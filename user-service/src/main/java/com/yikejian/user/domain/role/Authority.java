package com.yikejian.user.domain.role;

import com.google.common.collect.Lists;
import com.yikejian.user.api.v1.dto.AuthorityDto;

import java.util.Arrays;
import java.util.List;

/**
 * @author jackalope
 * @Title: Authority
 * @Package com.yikejian.user.domain.role
 * @Description: TODO
 * @date 2018/1/14 11:40
 */
public enum Authority {

    // 系统管理
//    SYSTEM("SYSTEM", "系统管理"),
    // 角色
    ROLE_READ("ROLE_READ", "查看角色"),
    ROLE_WRITE("ROLE_WRITE", "编辑角色"),
    // 用户
    USER_READ("USER_READ", "查看用户"),
    USER_WRITE("USER_WRITE", "编辑用户"),
    // 日志
    LOG_READ("LOG_READ", "查看日志"),
    // 客户管理
//    CUSTOMER("CUSTOMER", "客户管理"),
    CUSTOMER_READ("CUSTOMER_READ", "查看客户"),
    CUSTOMER_WRITE("CUSTOMER_WRITE", "编辑客户"),
    // 订单管理
//    ORDER("ORDER", "订单管理"),
    ORDER_READ("ORDER_READ", "查看订单"),
    ORDER_WRITE("ORDER_WRITE", "编辑订单"),
    // 信息管理
//    INFO("INFO", "信息管理"),
    STORE_READ("STORE_READ", "查看店铺"),
    STORE_WRITE("STORE_WRITE", "编辑店铺"),
    PRODUCT_READ("PRODUCT_READ", "查看产品"),
    PRODUCT_WRITE("PRODUCT_WRITE", "编辑产品"),
    DEVICE_READ("DEVICE_READ", "查看设备"),
    DEVICE_WRITE("DEVICE_WRITE", "编辑设备"),
    // 预约管理
    INVENTORY_READ("INVENTORY_READ", "查看预约"),
    INVENTORY_WRITE("INVENTORY_WRITE", "编辑预约"),;

    private String code;
    private String name;
    private static final String SPLITTER = ",";

    Authority(String code, String name) {
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

    public static List<AuthorityDto> getAll() {
        List<AuthorityDto> authorityDtoList = Lists.newArrayList();
        for (Authority authority : Authority.values()) {
            authorityDtoList.add(new AuthorityDto(authority.getCode(), authority.getName()));
        }
        return authorityDtoList;
    }

    public static String getAdminAuthority() {
        StringBuilder sbd = new StringBuilder();
//        sbd.append(SYSTEM.name()).append(SPLITTER);
        sbd.append(ROLE_READ.name()).append(SPLITTER);
        sbd.append(ROLE_WRITE.name()).append(SPLITTER);
        sbd.append(USER_READ.name()).append(SPLITTER);
        sbd.append(USER_WRITE.name()).append(SPLITTER);
        sbd.append(LOG_READ.name()).append(SPLITTER);
//        sbd.append(CUSTOMER.name()).append(SPLITTER);
        sbd.append(CUSTOMER_READ.name()).append(SPLITTER);
        sbd.append(CUSTOMER_WRITE.name()).append(SPLITTER);
//        sbd.append(ORDER.name()).append(SPLITTER);
        sbd.append(ORDER_READ.name()).append(SPLITTER);
        sbd.append(ORDER_WRITE.name()).append(SPLITTER);
//        sbd.append(INFO.name()).append(SPLITTER);
        sbd.append(STORE_READ.name()).append(SPLITTER);
        sbd.append(STORE_WRITE.name()).append(SPLITTER);
        sbd.append(PRODUCT_READ.name()).append(SPLITTER);
        sbd.append(PRODUCT_WRITE.name()).append(SPLITTER);
        sbd.append(DEVICE_READ.name()).append(SPLITTER);
        sbd.append(DEVICE_WRITE.name()).append(SPLITTER);
        sbd.append(INVENTORY_READ.name()).append(SPLITTER);
        sbd.append(INVENTORY_WRITE.name());
        return sbd.toString();
    }

    public static String getSystemManageAuthority() {
        StringBuilder sbd = new StringBuilder();
//        sbd.append(SYSTEM.name()).append(SPLITTER);
        sbd.append(ROLE_READ.name()).append(SPLITTER);
        sbd.append(ROLE_WRITE.name()).append(SPLITTER);
        sbd.append(USER_READ.name()).append(SPLITTER);
        sbd.append(USER_WRITE.name()).append(SPLITTER);
        sbd.append(LOG_READ.name());
        return sbd.toString();
    }

    public static String getInfoManageAuthority() {
        StringBuilder sbd = new StringBuilder();
//        sbd.append(INFO.name()).append(SPLITTER);
        sbd.append(STORE_READ.name()).append(SPLITTER);
        sbd.append(STORE_WRITE.name()).append(SPLITTER);
        sbd.append(PRODUCT_READ.name()).append(SPLITTER);
        sbd.append(PRODUCT_WRITE.name()).append(SPLITTER);
        sbd.append(DEVICE_READ.name()).append(SPLITTER);
        sbd.append(DEVICE_WRITE.name()).append(SPLITTER);
        sbd.append(INVENTORY_READ.name()).append(SPLITTER);
        sbd.append(INVENTORY_WRITE.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static String getCustomerManageAuthority() {
        StringBuilder sbd = new StringBuilder();
//        sbd.append(CUSTOMER.name()).append(SPLITTER);
        sbd.append(CUSTOMER_READ.name()).append(SPLITTER);
        sbd.append(CUSTOMER_WRITE.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static String getOrderManageAuthority() {
        StringBuilder sbd = new StringBuilder();
//        sbd.append(ORDER.name()).append(SPLITTER);
        sbd.append(ORDER_READ.name()).append(SPLITTER);
        sbd.append(ORDER_WRITE.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static String getCustomerAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(CUSTOMER_READ.name()).append(SPLITTER);
        sbd.append(CUSTOMER_WRITE.name()).append(SPLITTER);
        sbd.append(ORDER_READ.name()).append(SPLITTER);
        sbd.append(ORDER_WRITE.name()).append(SPLITTER);
        sbd.append(STORE_READ.name()).append(SPLITTER);
        sbd.append(PRODUCT_READ.name()).append(SPLITTER);
        sbd.append(INVENTORY_READ.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static List<String> transToArray(String authorities) {
        return Arrays.asList(authorities.split(SPLITTER));
    }

}
