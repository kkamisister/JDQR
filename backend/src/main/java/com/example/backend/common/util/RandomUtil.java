package com.example.backend.common.util;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomUtil {

  // 6자리 랜덤 숫자를 생성하는 메서드
  public static String generateRandomSixDigitNumber() {
    Random random = new Random();
    int min = 100_000; // Minimum 6-digit number (100000)
    int max = 999_999; // Maximum 6-digit number (999999)
    int randomNumber = random.nextInt(max - min + 1) + min;

    return String.format("%06d", randomNumber);
  }

  // 8자리 random 문자열을 생성하는 메서드
  // 문자로 가능한 종류 : 알파벳 대소문자 + 숫자
  public static String generateRandomString() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder(8);
    for (int i = 0; i < 8; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }
    return sb.toString();
  }

}
