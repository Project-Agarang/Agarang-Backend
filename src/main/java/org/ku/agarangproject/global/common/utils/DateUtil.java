package org.ku.agarangproject.global.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

  public static LocalDate convertStringToLocalDate(String dateStr) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    try {
      return LocalDate.parse(dateStr, formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException();
    }
  }

  public static LocalDate findDate(LocalDate localDate, int days) {
    return localDate.plusDays(days);
  }

  public static String formatLocalDateTime(LocalDateTime localDateTime, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return localDateTime.format(formatter);
  }

  public static LocalDate getStartOfMonth(YearMonth yearMonth) {
    return yearMonth.atDay(1);
  }

  public static LocalDate getEndOfMonth(YearMonth yearMonth) {
    return yearMonth.atEndOfMonth();
  }
  public static YearMonth convertStringToYearMonth(String dateStr) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
    return YearMonth.parse(dateStr, formatter);
  }
}
