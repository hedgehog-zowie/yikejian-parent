package com.yikejian.customer.util;

import java.util.Random;

/**
 * <code>CodeUtils</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/2/6 14:00
 */
public final class CodeUtils {

    private static final Random random = new Random();
    private static final Integer MAX = 10000;

    public static String generateCode() {
        return String.format("%04d", random.nextInt(MAX));
    }

}
