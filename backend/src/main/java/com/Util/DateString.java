package com.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Represents an object with a String representing a date. 
public class DateString implements Comparable<DateString> {
    private String dateString;
    private Date dateValue;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public DateString(String date) {
        this.dateString = date;
        this.dateValue = parseDate(date);
    }

    public String getDate() {
        return this.dateString;
    }

    private Date getDateValue() {
        return this.dateValue;
    }

    private Date parseDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.\n", e);
        }
    }

    @Override
    public int compareTo(DateString otherDate) {
        return this.getDateValue().compareTo(otherDate.getDateValue());
    }

    /**
     * Calculates the next day from the current date represented by this DateString object.
     * 
     * @return a new DateString object representing the next day
     */
    public DateString nextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateValue);
        calendar.add(Calendar.DATE, 1);
        return new DateString(format.format(calendar.getTime()));
    }

    /**
     * Calculates the next n days from the current date represented by this DateString object.
     * 
     * @param numDays the number of days to move forward
     * @return a new DateString object representing the new day
     */
    public DateString incrementDays(int numDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateValue);
        calendar.add(Calendar.DATE, numDays);
        return new DateString(format.format(calendar.getTime()));
    }
}
