package com.yikejian.user.domain.role;

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

    ROLE_QUERY,
    ROLE_EDIT,
    USER_QUERY,
    USER_EDIT,
    LOG_QUERY,
    CUSTOMER_QUERY,
    CUSTOMER_EDIT,
    ORDER_QUERY,
    ORDER_EDIT,
    STORE_QUERY,
    STORE_EDIT,
    PRODUCT_QUERY,
    PRODUCT_EDIT,
    DEVICE_QUERY,
    DEVICE_EDIT,
    BOOK_QUERY,
    BOOK_EDIT,;

    private static final String SPLITTER = ",";

    public static String getAdminAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(ROLE_QUERY.name()).append(SPLITTER);
        sbd.append(ROLE_EDIT.name()).append(SPLITTER);
        sbd.append(USER_QUERY.name()).append(SPLITTER);
        sbd.append(USER_EDIT.name()).append(SPLITTER);
        sbd.append(LOG_QUERY.name()).append(SPLITTER);
        sbd.append(CUSTOMER_QUERY.name()).append(SPLITTER);
        sbd.append(CUSTOMER_EDIT.name()).append(SPLITTER);
        sbd.append(ORDER_QUERY.name()).append(SPLITTER);
        sbd.append(ORDER_EDIT.name()).append(SPLITTER);
        sbd.append(STORE_QUERY.name()).append(SPLITTER);
        sbd.append(STORE_EDIT.name()).append(SPLITTER);
        sbd.append(PRODUCT_QUERY.name()).append(SPLITTER);
        sbd.append(PRODUCT_EDIT.name()).append(SPLITTER);
        sbd.append(DEVICE_QUERY.name()).append(SPLITTER);
        sbd.append(DEVICE_EDIT.name()).append(SPLITTER);
        sbd.append(BOOK_QUERY.name()).append(SPLITTER);
        sbd.append(BOOK_EDIT.name());
        return sbd.toString();
    }

    public static String getSystemAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(ROLE_QUERY.name()).append(SPLITTER);
        sbd.append(ROLE_EDIT.name()).append(SPLITTER);
        sbd.append(USER_QUERY.name()).append(SPLITTER);
        sbd.append(USER_EDIT.name()).append(SPLITTER);
        sbd.append(LOG_QUERY.name());
        return sbd.toString();
    }

    public static String getInfoAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(STORE_QUERY.name()).append(SPLITTER);
        sbd.append(STORE_EDIT.name()).append(SPLITTER);
        sbd.append(PRODUCT_QUERY.name()).append(SPLITTER);
        sbd.append(PRODUCT_EDIT.name()).append(SPLITTER);
        sbd.append(DEVICE_QUERY.name()).append(SPLITTER);
        sbd.append(DEVICE_EDIT.name()).append(SPLITTER);
        sbd.append(BOOK_QUERY.name()).append(SPLITTER);
        sbd.append(BOOK_EDIT.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static String getCustomerAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(CUSTOMER_QUERY.name()).append(SPLITTER);
        sbd.append(CUSTOMER_EDIT.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static String getOrderAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(ORDER_QUERY.name()).append(SPLITTER);
        sbd.append(ORDER_EDIT.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static List<String> transToArray(String authorities){
        return Arrays.asList(authorities.split(SPLITTER));
    }

}
