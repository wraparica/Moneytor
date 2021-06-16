package com.wraparica.moneytor.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class DateUtils {

    public static final String TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIMESTAMP_SHORT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String TIMESTAMP_CLEAN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_DATE = "yyyy-MM-dd";
    public static final String TIMESTAMP_YEAR_MONTH = "yyyy-MM";
    public static final String TIMESTAMP_DATE_T = "yyyy-MM-dd'T'";
    public static final String TIMESTAMP_TIME = "HH:mm:ss";
    public static final String TIMESTAMP_TIME_LONG = "HH:mm:ss.SSS";
    public static final String TIMESTAMP_DATE_TIME = "yyyy-MM-dd hh:mm aa";
    public static final String TIMESTAMP_TIME_LONG_UNFORMATTED = "'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_TIME_12_HOUR_SHORT = "MMM dd, yyyy hh:mm aa";
    public static final String DATE_TIME_12_HOUR_SHORT_PARENTHESIS = "MMM dd, yyyy (hh:mm aa)";
    public static final String DATE_TIME_12_HOUR = "MMM dd, yyyy hh:mm:ss aa";
    public static final String DATE_TIME_12_HOUR_LONG = "MMM dd, yyyy hh:mm:ss.SSS aa";
    public static final String DATE_TIME_24_HOUR = "MMM dd, yyyy hh:mm:ss";
    public static final String DATE_TIME_24_HOUR_NO_SECONDS = "MMM dd, yyyy hh:mm";
    public static final String DATE_TIME_24_HOUR_LONG = "MMM dd, yyyy hh:mm:ss.SSS";
    public static final String DATE = "MMM dd, yyyy";
    public static final String DATE_LONG = "EEEE, MMMM dd, yyyy";
    public static final String DAY_OF_WEEK = "EEEE";
    public static final String DAY_DATE_SHORT = "EEEE, MMM dd";
    public static final String TIME_12_HOUR = "hh:mm aa";
    public static final String TIME_12_HOUR_SHORT = "hh:mm:ss aa";
    public static final String TIME_12_HOUR_LONG = "hh:mm:ss.SSS aa";
    public static final String TIME_24_HOUR = "HH:mm:ss";
    public static final String TIME_24_HOUR_SHORT = "HH:mm";
    public static final String TIME_24_HOUR_LONG = "HH:mm:ss.SSS";
    public static final String MONTH_DAY_LONG = "MMMM dd";
    public static final String MONTH_DAY = "MMM dd";
    public static final String DAY_OF_MONTH_DAY = "dd EEE";
    public static final String YEAR = "yyyy";
    public static final String MOM_SUFFIX = "MMMddyyyyHHmmss";
    public static final String FILE_PREFIX = "yyyyMMddHHmmssSSS";
    public static final String TIME_HOURMINUTE_FORMAT = "%02d:%02d:00";
    public static final String EXPORT_SUFFIX = "yyyy_MM_dd_HHmmssSSS";
    public static final String HOURS = "HH";
    public static final String MINUTES = "mm";
    public static final int RETENTION_PERIOD = 7;

    public static String convert(@NonNull String fromFormat, @NonNull String toFormat, @NonNull String time) {
        if (time == null) {
            return null;
        }
        final SimpleDateFormat fromSDF = new SimpleDateFormat(fromFormat, Locale.getDefault());
        final SimpleDateFormat toSDF = new SimpleDateFormat(toFormat, Locale.getDefault());
        try {
            time = toSDF.format(fromSDF.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String format(Date date, @NonNull String format) {
        if (date == null) {
            return null;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    public static long convertToMilliseconds(String fromFormat, String time) {
        final SimpleDateFormat fromSDF = new SimpleDateFormat(fromFormat, Locale.getDefault());
        long timeInMilliseconds = 0;
        try {
            Date mDate = fromSDF.parse(time);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static long convertToMillisecondsUTC(String fromFormat, String time) {
        final SimpleDateFormat fromSDF = new SimpleDateFormat(fromFormat, Locale.getDefault());
        fromSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        long timeInMilliseconds = 0;
        try {
            Date mDate = fromSDF.parse(time);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static String convertToLocal(@NonNull String fromFormat, @NonNull String toFormat, @NonNull String time) {
        final SimpleDateFormat fromSDF = new SimpleDateFormat(fromFormat, Locale.getDefault());
        fromSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        final SimpleDateFormat toSDF = new SimpleDateFormat(toFormat, Locale.getDefault());
        try {
            time = toSDF.format(fromSDF.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    public static String getWorkDuration(String inputPattern, String dateToFormat){
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateToFormat);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.get(Calendar.HOUR_OF_DAY);
            if (calendar.get(Calendar.MINUTE) > 0) {
                str = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)).concat(" Hours ").concat(String.valueOf(calendar.get(Calendar.MINUTE))).concat(" Minutes ");
            } else {
                str = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)).concat(" Hours ");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String convertToUTC(@NonNull String time) {
        return convertToUTC(TIMESTAMP, TIMESTAMP, time);
    }

    public static String convertToUTC(@NonNull String fromFormat, @NonNull String toFormat, @NonNull String time) {
        final SimpleDateFormat fromSDF = new SimpleDateFormat(fromFormat, Locale.getDefault());
        final SimpleDateFormat toSDF = new SimpleDateFormat(toFormat, Locale.getDefault());
        toSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            time = toSDF.format(fromSDF.parse(time));
        } catch (ParseException e) {
            e.getMessage();
        }
        return time;
    }

    public static String getUTCTimestamp() {
        final SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    public static String getUTCTimestamp(@NonNull String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    @Nullable
    public static String convertDateToTimestamp(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP, Locale.getDefault());
        return sdf.format(date);
    }

    public static String getLocalTimestamp() {
        final SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP, Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String getLocalStartOfDay() {
        final SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP, Locale.getDefault());
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(Calendar.getInstance().getTime());
        calendarDate.set(Calendar.HOUR, 0);
        calendarDate.set(Calendar.MINUTE, 0);
        calendarDate.set(Calendar.SECOND, 0);
        calendarDate.set(Calendar.MILLISECOND, 0);
        calendarDate.set(Calendar.AM_PM, Calendar.AM);
        Date currentDate = calendarDate.getTime();
        return sdf.format(currentDate);
    }

    public static String getLocalEndOfDay() {
        final SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP, Locale.getDefault());
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(Calendar.getInstance().getTime());
        calendarDate.set(Calendar.HOUR, 23);
        calendarDate.set(Calendar.MINUTE, 59);
        calendarDate.set(Calendar.SECOND, 59);
        calendarDate.set(Calendar.MILLISECOND, 0);
        Date currentDate = calendarDate.getTime();
        return sdf.format(currentDate);
    }

    public static String getStartOfDay() {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_12_HOUR);
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(Calendar.getInstance().getTime());
        calendarDate.set(Calendar.AM_PM, Calendar.AM);
        calendarDate.set(Calendar.HOUR, 0);
        calendarDate.set(Calendar.MINUTE, 0);
        calendarDate.set(Calendar.SECOND, 0);
        Date currentDate = calendarDate.getTime();
        return sdf.format(currentDate);
    }

    public static String getEndOfDay() {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_12_HOUR);
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(Calendar.getInstance().getTime());
        calendarDate.set(Calendar.AM_PM, Calendar.PM);
        calendarDate.set(Calendar.HOUR, 11);
        calendarDate.set(Calendar.MINUTE, 59);
        calendarDate.set(Calendar.SECOND, 59);
        Date currentDate = calendarDate.getTime();
        return sdf.format(currentDate);
    }

    public static String getLocalTimestamp(@NonNull String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String convertEpochToTimestamp(long epochTime) {
        final SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP, Locale.getDefault());
        return sdf.format(new Date(epochTime));
    }

    public static long convertToEpoch(String format, String timestamp) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = sdf.parse(timestamp);
            return date == null ? 0 : date.getTime() / 1000;
        } catch (ParseException e) {
            return 0;
        }
    }

    public static long convertToEpochMillis(String format, String timestamp) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = sdf.parse(timestamp);
            return date == null ? 0 : date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String convertEpochToTimestamp(long epochTime, @NonNull String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(epochTime));
    }

    public static String getHoursAfter(@NonNull final String timestamp, @NonNull String fromFormat, @NonNull String toFormat, int hours) {
        String output = timestamp;
        final SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat, Locale.getDefault());
        final SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat, Locale.getDefault());
        try {
            Date date = sdfFrom.parse(output);
            date.setTime(date.getTime() + (1000 * 60 * 60 * hours));
            output = sdfTo.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String getHoursAfterUTC(@NonNull String format, int hours) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        date.setTime(date.getTime() + (1000 * 60 * 60 * hours));
        return sdf.format(date);
    }

    public static String getHoursAfterLocal(@NonNull String format, int hours) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date();
        date.setTime(date.getTime() + (1000 * 60 * 60 * hours));
        return sdf.format(date);
    }

    public static String getMonthStartUTC(@NonNull final String timestamp, @NonNull String fromFormat, @NonNull String toFormat) {
        final SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat, Locale.getDefault());
        final SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat, Locale.getDefault());
        sdfTo.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(sdfFrom.parse(timestamp));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return sdfTo.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return timestamp;
        }
    }

    public static String getMonthEndUTC(@NonNull final String timestamp, @NonNull String fromFormat, @NonNull String toFormat) {
        final SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat, Locale.getDefault());
        final SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat, Locale.getDefault());
        sdfTo.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(sdfFrom.parse(timestamp));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
            return sdfTo.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return timestamp;
        }
    }

    /**
     * @param time
     * @return adjusted utc time only in minutes/
     */
    public static String getAdjustedCurrentUTCTime(int time) {
        return getAdjustedCurrentUTCTime(time, Calendar.MINUTE);
    }

    /**
     * @param time
     */
    public static String getAdjustedCurrentUTCTime(int time, int timeUnit) {
        final SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(timeUnit, time);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getAdjustedCurrentLocalTime(int units, int timeUnit) {
        return getAdjustedCurrentLocalTime(TIMESTAMP, units, timeUnit);
    }

    public static String getAdjustedCurrentLocalTime(String format, int units, int timeUnit) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(timeUnit, units);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getUpcomingLocalTimeFilter() {
        final SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -30);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getStartTimeAdjusted(@NonNull String startTime, @NonNull int minutes, @NonNull boolean timedIn) {
        final SimpleDateFormat sdf = new SimpleDateFormat(TIME_24_HOUR, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!timedIn) {
            calendar.add(Calendar.MINUTE, -minutes);
        } else {
            calendar.add(Calendar.MINUTE, minutes);
        }
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public static String formatYearMonthDayToInputFormat(int year, int month, int day) {
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year, (month + 1), day);
    }

    public static String formatYearMonthDayToTimestampDateFormat(int year, int month, int day) {
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
    }

    public static String getTimeLapsed(@NonNull String format, @NonNull String startDate, @NonNull String endDate) {
        StringBuilder output = new StringBuilder();
        SimpleDateFormat timeFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date start = timeFormat.parse(startDate);
            Date end = timeFormat.parse(endDate);
            // Data type loophole!
            Long difference = end.getTime() - start.getTime();
            Long holder = difference / 3600000;
            if (holder > 24) {
                holder = holder / 24;
                output.append(holder).append("d ");
                holder = (difference / 3600000) - (holder * 24);
            }
            if (holder > 0) {
                output.append(holder).append("hr ");
            }
            holder = (difference % 3600000) / 60000;
            if (holder > 0) {
                output.append(holder).append("min");
            }

        } catch (ParseException e) {
            e.getMessage();
            return output.toString();
        }
        return output.toString();
    }

    public static String getTimeLapsed(long elapsedTime) {
        StringBuilder output = new StringBuilder();
        Long holder = elapsedTime / 3600000;
        if (holder > 24) {
            holder = holder / 24;
            output.append(holder).append("d ");
            holder = (elapsedTime / 3600000) - (holder * 24);
        }
        if (holder > 0) {
            output.append(holder).append("hr ");
        }
        holder = (elapsedTime % 3600000) / 60000;
        if (holder > 0) {
            output.append(holder).append("min");
        }

        return output.toString();
    }

    public static long getDifference(@NonNull String format, @NonNull String startDate, @NonNull String endDate) {
        if (startDate == null || endDate == null || format == null) {
            return 0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            return Math.abs(start.getTime() - end.getTime());
        } catch (ParseException e) {
            return 0;
        }
    }

    public static long getRawDifference(@NonNull String format, @NonNull String startDate, @NonNull String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            return start.getTime() - end.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static boolean isInputAGreaterThanB(String format, String date1, String date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date inputA = dateFormat.parse(date1);
            Date inputB = dateFormat.parse(date2);
            if (inputA.after(inputB)) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static String getNextHourLocal(int hours) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.HOUR, hours);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_TIME, Locale.getDefault());
        return sdf.format(c.getTime());
    }

    public static String formatHourMinuteToStringFormat(int hour, int minute) {
        return String.format(Locale.getDefault(), TIME_HOURMINUTE_FORMAT, hour, minute);
    }

    public static Integer extractHour(String input) {
        try {
            return Integer.parseInt(input.substring(0, 2));
        } catch (Exception e) {
            e.printStackTrace();
            return 12;
        }
    }

    public static Integer extractMinute(String input) {
        try {
            return Integer.parseInt(input.substring(3, 5));
        } catch (Exception e) {
            e.printStackTrace();
            return 50;
        }
    }

    /**
     * converts a string in "hh:mm:ss" format or "hh:mm" format to seconds
     *
     * @param time String in "hh:mm:sS" or "hh:mm" format
     * @return time in seconds
     */
    public static long convertTimeToSeconds(String time) {
        int h = 0;
        int m = 0;
        int s = 0;

        String[] times = time.split(":");
        try {
            if (times.length == 2) {
                h = Integer.parseInt(times[0]);
                s = Integer.parseInt(times[1]);
            } else if (times.length == 3) {
                h = Integer.parseInt(times[0]);
                m = Integer.parseInt(times[1]);
                s = Integer.parseInt(times[2]);
            }
        } catch (NumberFormatException e) {
            // do nothing
        }

        long output = (h * 3600) + (m * 60) + s;

        return output;
    }

    public static String formatSeconds(long seconds) {
        return String.format(Locale.getDefault(), "%02d:%02d", seconds / 3600, (seconds % 3600) / 60);
    }

    /**
     * @param timestamp time to be adjusted
     * @param timeUnit  Calendar time unit
     * @param offset    amount of time to offset
     * @return adjusted utc time based on parameters.
     */
    public static String getAdjustedTime(String timestamp, @DayType String inputFormat, @DayType String outputFormat, int timeUnit, int offset) {
        if (timestamp == null) {
            return null;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(inputFormat, Locale.getDefault());
        final SimpleDateFormat sdfOut = new SimpleDateFormat(outputFormat, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(timestamp));
        } catch (ParseException e) {
            return timestamp;
        }
        calendar.add(timeUnit, offset);
        Date date = calendar.getTime();
        return sdfOut.format(date);
    }

    public static String getWorkHours(String timestamp, String format){
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(timestamp));
        } catch (ParseException e) {
            return timestamp;
        }
        String workHours;
        if (calendar.get(Calendar.MINUTE) > 0) {
            workHours = calendar.get(Calendar.HOUR_OF_DAY) + "HR " + calendar.get(Calendar.MINUTE) + "MIN ";
        } else {
            workHours = calendar.get(Calendar.HOUR_OF_DAY) + " HR ";
        }
        return workHours;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef(value = {TIMESTAMP, TIMESTAMP_SHORT, TIMESTAMP_CLEAN, TIMESTAMP_DATE,
            TIMESTAMP_YEAR_MONTH, TIMESTAMP_DATE_T, TIMESTAMP_TIME, TIMESTAMP_TIME_LONG,
            DATE_TIME_12_HOUR_SHORT, DATE_TIME_12_HOUR, DATE_TIME_12_HOUR_LONG, DATE_TIME_24_HOUR,
            DATE_TIME_24_HOUR_NO_SECONDS, DATE_TIME_24_HOUR_LONG, DATE, DATE_LONG, DAY_OF_WEEK,
            TIME_12_HOUR, TIME_12_HOUR_SHORT, TIME_12_HOUR_LONG, TIME_24_HOUR, TIME_24_HOUR_SHORT,
            TIME_24_HOUR_LONG, MONTH_DAY_LONG, MONTH_DAY, YEAR, MOM_SUFFIX, FILE_PREFIX,
            TIME_HOURMINUTE_FORMAT})
    @interface DayType {
    }

    public static String getAdjustedTime(String timestamp, String inputFormat, int hours, int minutes) {
        final SimpleDateFormat sdf = new SimpleDateFormat(inputFormat, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(timestamp));
        } catch (ParseException e) {
            return timestamp;
        }
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public static String computeWorkHours(String txtStartTime, String txtEndTime)
    {
        long diff = 0;
        String returnValue = null;
        DateFormat df = new java.text.SimpleDateFormat(TIMESTAMP_DATE_TIME, Locale.getDefault());
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = df.parse(txtStartTime);
            endTime = df.parse(txtEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (endTime.getTime() > startTime.getTime()) {
                diff = endTime.getTime() - startTime.getTime();
                returnValue = convertElapsedToActualTime(diff);
            }
        }
        catch (NullPointerException e) {

        }

        return returnValue;
    }

    public static String convertElapsedToActualTime(long elapsedTime) {
        final long hour = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        final long min = TimeUnit.MILLISECONDS.toMinutes(elapsedTime - TimeUnit.HOURS.toMillis(hour));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(elapsedTime - TimeUnit.HOURS.toMillis(hour) - TimeUnit.MINUTES.toMillis(min));

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec);
    }


}
