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

    ROLE_READ,
    ROLE_WRITE,
    USER_READ,
    USER_WRITE,
    LOG_READ,
    CUSTOMER_READ,
    CUSTOMER_WRITE,
    ORDER_READ,
    ORDER_WRITE,
    STORE_READ,
    STORE_WRITE,
    PRODUCT_READ,
    PRODUCT_WRITE,
    DEVICE_READ,
    DEVICE_WRITE,
    BOOK_READ,
    BOOK_WRITE,;

    private static final String SPLITTER = ",";

    public static String getAdminAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(ROLE_READ.name()).append(SPLITTER);
        sbd.append(ROLE_WRITE.name()).append(SPLITTER);
        sbd.append(USER_READ.name()).append(SPLITTER);
        sbd.append(USER_WRITE.name()).append(SPLITTER);
        sbd.append(LOG_READ.name()).append(SPLITTER);
        sbd.append(CUSTOMER_READ.name()).append(SPLITTER);
        sbd.append(CUSTOMER_WRITE.name()).append(SPLITTER);
        sbd.append(ORDER_READ.name()).append(SPLITTER);
        sbd.append(ORDER_WRITE.name()).append(SPLITTER);
        sbd.append(STORE_READ.name()).append(SPLITTER);
        sbd.append(STORE_WRITE.name()).append(SPLITTER);
        sbd.append(PRODUCT_READ.name()).append(SPLITTER);
        sbd.append(PRODUCT_WRITE.name()).append(SPLITTER);
        sbd.append(DEVICE_READ.name()).append(SPLITTER);
        sbd.append(DEVICE_WRITE.name()).append(SPLITTER);
        sbd.append(BOOK_READ.name()).append(SPLITTER);
        sbd.append(BOOK_WRITE.name());
        return sbd.toString();
    }

    public static String getSystemAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(ROLE_READ.name()).append(SPLITTER);
        sbd.append(ROLE_WRITE.name()).append(SPLITTER);
        sbd.append(USER_READ.name()).append(SPLITTER);
        sbd.append(USER_WRITE.name()).append(SPLITTER);
        sbd.append(LOG_READ.name());
        return sbd.toString();
    }

    public static String getInfoAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(STORE_READ.name()).append(SPLITTER);
        sbd.append(STORE_WRITE.name()).append(SPLITTER);
        sbd.append(PRODUCT_READ.name()).append(SPLITTER);
        sbd.append(PRODUCT_WRITE.name()).append(SPLITTER);
        sbd.append(DEVICE_READ.name()).append(SPLITTER);
        sbd.append(DEVICE_WRITE.name()).append(SPLITTER);
        sbd.append(BOOK_READ.name()).append(SPLITTER);
        sbd.append(BOOK_WRITE.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static String getCustomerAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(CUSTOMER_READ.name()).append(SPLITTER);
        sbd.append(CUSTOMER_WRITE.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static String getOrderAuthority() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(ORDER_READ.name()).append(SPLITTER);
        sbd.append(ORDER_WRITE.name()).append(SPLITTER);
        return sbd.toString();
    }

    public static List<String> transToArray(String authorities) {
        return Arrays.asList(authorities.split(SPLITTER));
    }

}
