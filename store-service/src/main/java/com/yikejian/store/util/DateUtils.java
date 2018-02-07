package com.yikejian.store.util;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <code>DateUtil</code>.
 * 日期处理工具类
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/6/9 14:42
 */
public final class DateUtils {

    /**
     * 日期格式
     */
    private static final String DAY_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter dayTimeFormatter = DateTimeFormatter.ofPattern(DAY_FORMAT);

    private DateUtils() {
    }

    public static LocalDate dayStrToDate(final String dateStr) {
        return LocalDate.parse(dateStr, dayTimeFormatter);
    }

    public static String dateToDayStr(final LocalDate localDate) {
        return localDate.format(dayTimeFormatter);
    }

}
