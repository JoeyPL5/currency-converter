package com.Util;

import org.junit.Test;
import static org.junit.Assert.*;

public class DateStringTests {

    @Test
    public void testCompareToLessThan() {
        DateString date1 = new DateString("2024-03-28");
        DateString date2 = new DateString("2024-03-29");
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    public void testCompareToGreaterThan() {
        DateString date1 = new DateString("2024-03-29");
        DateString date2 = new DateString("2024-03-28");
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    public void testCompareToEqual() {
        DateString date1 = new DateString("2024-03-29");
        DateString date2 = new DateString("2024-03-29");
        assertTrue(date1.compareTo(date2) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDate() {
        new DateString("Invalid Date"); 
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDateFormatSlashes() {
        new DateString("2024/03/28"); 
    }

    @Test
    public void testNextDay() {
        DateString date = new DateString("2024-03-28");
        DateString nextDayDate = date.nextDay();
        assertEquals("2024-03-29", nextDayDate.getDate());
    }

    @Test
    public void testNextDayLeapYear() {
        DateString date = new DateString("2024-02-28");
        DateString nextDate = date.nextDay();
        assertEquals("2024-02-29", nextDate.getDate());
    }

    @Test
    public void testNextDayEndOfMonth() {
        DateString date = new DateString("2024-10-31");
        DateString nextDate = date.nextDay();
        assertEquals("2024-11-01", nextDate.getDate());
    }

    @Test
    public void testNextDayEndOfYear() {
        DateString date = new DateString("2023-12-31");
        DateString nextDate = date.nextDay();
        assertEquals("2024-01-01", nextDate.getDate());
    }
}