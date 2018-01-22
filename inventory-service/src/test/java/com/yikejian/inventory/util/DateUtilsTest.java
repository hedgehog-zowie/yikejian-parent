package com.yikejian.inventory.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * <code>DateUtilTest</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/22 16:12
 */
public class DateUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilsTest.class);

    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void pieceTimeStrToDateTest() {
        String pieceTimeString = "201801011000";
        LocalDateTime localDateTime = null;
        try {
            localDateTime = DateUtils.pieceTimeStrToDate(pieceTimeString);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        assertNotNull(localDateTime);
        String pieceTimeString2 = "20180101";
        LocalDateTime localDateTime2 = null;
        try {
            localDateTime2 = DateUtils.pieceTimeStrToDate(pieceTimeString2);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        assertNull(localDateTime2);
        String pieceTimeString3 = "20180101100000";
        LocalDateTime localDateTime3 = null;
        try {
            localDateTime3 = DateUtils.pieceTimeStrToDate(pieceTimeString3);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        assertNull(localDateTime3);
        String pieceTimeString4 = null;
        LocalDateTime localDateTime4 = null;
        try {
            localDateTime4 = DateUtils.pieceTimeStrToDate(pieceTimeString4);
        } catch (RuntimeException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        assertNull(localDateTime4);
    }

    @Test
    public void dateToPieceTimeStrTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertEquals("201801221625", DateUtils.dateToPieceTimeStr(localDateTime));
    }

    @Test
    public void dayStrToDateTest() {
        String dayString = "20180101";
        LocalDate localDate = null;
        try {
            localDate = DateUtils.dayStrToDate(dayString);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        assertNotNull(localDate);
        String dayString2 = "201801011010";
        LocalDate localDate2 = null;
        try {
            localDate2 = DateUtils.dayStrToDate(dayString2);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        assertNull(localDate2);
        String dayString3 = "2018";
        LocalDate localDate3 = null;
        try {
            localDate3 = DateUtils.dayStrToDate(dayString3);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        assertNull(localDate3);
    }

    @Test
    public void dateToDayStrTest() {
        LocalDate localDate = LocalDate.now();
        assertEquals("20180122", DateUtils.dateToDayStr(localDate));
    }

    @Test
    public void generatePieceTimeOfDayTest(){
        String day = "20180120";
        String start = "1000";
        String end = "1200";
        Integer duration = 30;
        List<String> pieceTimeList = DateUtils.generatePieceTimeOfDay(day, start, end, duration);
        assertEquals(4, pieceTimeList.size());
        duration = 40;
        pieceTimeList = DateUtils.generatePieceTimeOfDay(day, start, end, duration);
        assertEquals(3, pieceTimeList.size());
        duration = 50;
        pieceTimeList = DateUtils.generatePieceTimeOfDay(day, start, end, duration);
        assertEquals(2, pieceTimeList.size());
        duration = 60;
        pieceTimeList = DateUtils.generatePieceTimeOfDay(day, start, end, duration);
        assertEquals(2, pieceTimeList.size());
    }

}
