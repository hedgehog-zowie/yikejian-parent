package com.yikejian.inventory.util;

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
     * 时间片格式
     */
    private static final String PIECE_TIME_FORMAT = "yyyyMMddHHmm";
    private static final DateTimeFormatter pieceTimeFormatter = DateTimeFormatter.ofPattern(PIECE_TIME_FORMAT);
    /**
     * 日期格式
     */
    private static final String DAY_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter dayTimeFormatter = DateTimeFormatter.ofPattern(DAY_FORMAT);

    private DateUtils() {
    }

    public static LocalDateTime pieceTimeStrToDate(final String dateStr) {
        return LocalDateTime.parse(dateStr, pieceTimeFormatter);
    }

    public static String dateToPieceTimeStr(final LocalDateTime localDate) {
        return localDate.format(pieceTimeFormatter);
    }

    /**
     * 生成时间片
     *
     * @param day          日期（格式为yyyyMMdd）
     * @param start        开始时间（精确到分，如1020表示10点20分）
     * @param end          停止时间（精确到分，如1540表示15表40分）
     * @param unitDuration 单个时间片长度
     * @return
     */
    public static List<String> generatePieceTimeOfDay(String day, String start, String end, Integer unitDuration) {
        List<String> pieceTimeList = Lists.newArrayList();
        LocalDateTime startDateTime = pieceTimeStrToDate(day + start);
        LocalDateTime endDateTime = pieceTimeStrToDate(day + end).minusMinutes(unitDuration);
        LocalDateTime currentDateTime = startDateTime;
        LocalDateTime now = LocalDateTime.now();
        while (currentDateTime.compareTo(endDateTime) <= 0) {
            if (currentDateTime.compareTo(now) >= 0) {
                pieceTimeList.add(dateToPieceTimeStr(currentDateTime));
            }
            currentDateTime = currentDateTime.plusMinutes(unitDuration);
        }
        return pieceTimeList;
    }

    public static LocalDate dayStrToDate(final String dateStr) {
        return LocalDate.parse(dateStr, dayTimeFormatter);
    }

    public static String dateToDayStr(final LocalDate localDate) {
        return localDate.format(dayTimeFormatter);
    }

    public static List<String> getNeighborPieceTime(final String pieceTime,
                                                    final String start,
                                                    final String end,
                                                    final Integer duration,
                                                    final Integer unitDuration) {
        String day = pieceTime.substring(0, 8);
        LocalDateTime startDateTime = pieceTimeStrToDate(day + start);
        LocalDateTime endDateTime = pieceTimeStrToDate(day + end);
        LocalDateTime selfDateTime = pieceTimeStrToDate(pieceTime);
        // 根据duration和unitDuration重新计算时间间距
        Long newDuration = (long) Math.ceil(duration / unitDuration) * unitDuration;
        LocalDateTime minusDateTime = selfDateTime.minusMinutes(newDuration);
        LocalDateTime plusDateTime = selfDateTime.plusMinutes(newDuration);
        startDateTime = startDateTime.compareTo(minusDateTime) > 0 ? startDateTime : minusDateTime;
        endDateTime = endDateTime.compareTo(plusDateTime) < 0 ? endDateTime : plusDateTime;
        LocalDateTime currentDateTime = startDateTime;
        List<String> pieceTimeList = Lists.newArrayList();
        while (currentDateTime.compareTo(endDateTime) <= 0) {
            pieceTimeList.add(dateToPieceTimeStr(currentDateTime));
            currentDateTime = currentDateTime.plusMinutes(unitDuration);
        }
        return pieceTimeList;
    }

}
