package com.example.backend.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class TimeUtil {

  private final static ZoneId timeZoneUTC = ZoneId.of("UTC");
  private final static ZoneId timeZoneSeoul = ZoneId.of("Asia/Seoul");

  // LocalDateTime -> TimeStamp형으로 변환하는 메서드
  public static long getTimeStampFrom(LocalDateTime localDateTime){
    return localDateTime.atZone(timeZoneSeoul).toInstant().toEpochMilli();
  }

  // TimeStamp -> LocalDate 형으로 변환하는 메서드
  public static LocalDate getLocalDateFrom(long epochMillis){
    Instant instant = Instant.ofEpochMilli(epochMillis);
    return instant.atZone(timeZoneSeoul).toLocalDate();
  }

  // 현재 시간 기준 TimeStamp 형으로 변환하는 메서드
  public static long getCurrentTimeMillisUtc() {
    LocalDateTime localDateTime = LocalDateTime.now();
    return getTimeStampFrom(localDateTime);
  }

  // TimeStamp를 주어진 pattern에 해당하게 파싱하여 반환하는 메서드
  public static String convertEpochToDateString(long epochMillis, String pattern) {
    LocalDate localDate = Instant.ofEpochMilli(epochMillis).atZone(timeZoneSeoul).toLocalDate();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return localDate.format(formatter);
  }

  // localDate에 해당하는 TimeStamp를 계산 후 반환
  public static long toStartOfDaySeoul(LocalDate localDate) {
    return getTimeStampFrom(localDate.atStartOfDay(timeZoneSeoul).toLocalDateTime());
  }
}
