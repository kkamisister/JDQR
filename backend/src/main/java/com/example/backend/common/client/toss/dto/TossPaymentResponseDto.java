package com.example.backend.common.client.toss.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentResponseDto {

  private Boolean success;
  private String errorCode;
  private String errorMessage;
  @JsonProperty("mId")
  private String merchantId;

  @JsonProperty("version")
  private String version;

  @JsonProperty("paymentKey")
  private String paymentKey;

  @JsonProperty("status")
  private String status;

  @JsonProperty("lastTransactionKey")
  private String lastTransactionKey;

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("orderName")
  private String orderName;

  @JsonProperty("requestedAt")
  private String requestedAt;

  @JsonProperty("approvedAt")
  private String approvedAt;

  @JsonProperty("useEscrow")
  private boolean useEscrow;

  @JsonProperty("cultureExpense")
  private boolean cultureExpense;

  @JsonProperty("card")
  private Card card;

  @JsonProperty("virtualAccount")
  private VirtualAccount virtualAccount;

  @JsonProperty("type")
  private String type;

  @JsonProperty("country")
  private String country;

  @JsonProperty("isPartialCancelable")
  private boolean isPartialCancelable;

  @JsonProperty("receipt")
  private Receipt receipt;

  @JsonProperty("checkout")
  private Checkout checkout;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("totalAmount")
  private int totalAmount;

  @JsonProperty("balanceAmount")
  private int balanceAmount;

  @JsonProperty("suppliedAmount")
  private int suppliedAmount;

  @JsonProperty("vat")
  private int vat;

  @JsonProperty("taxFreeAmount")
  private int taxFreeAmount;

  @JsonProperty("method")
  private String method;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Card {
  @JsonProperty("issuerCode")
  private String issuerCode;

  @JsonProperty("acquirerCode")
  private String acquirerCode;

  @JsonProperty("number")
  private String number;

  @JsonProperty("installmentPlanMonths")
  private int installmentPlanMonths;

  @JsonProperty("isInterestFree")
  private boolean isInterestFree;

  @JsonProperty("interestPayer")
  private String interestPayer;

  @JsonProperty("approveNo")
  private String approveNo;

  @JsonProperty("useCardPoint")
  private boolean useCardPoint;

  @JsonProperty("cardType")
  private String cardType;

  @JsonProperty("ownerType")
  private String ownerType;

  @JsonProperty("acquireStatus")
  private String acquireStatus;

  @JsonProperty("amount")
  private int amount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class VirtualAccount {
  @JsonProperty("accountNumber")
  private String accountNumber;

  @JsonProperty("accountType")
  private String accountType;

  @JsonProperty("bankCode")
  private String bankCode;

  @JsonProperty("customerName")
  private String customerName;

  @JsonProperty("dueDate")
  private String dueDate;

  @JsonProperty("expired")
  private boolean expired;

  @JsonProperty("settlementStatus")
  private String settlementStatus;

  @JsonProperty("refundStatus")
  private String refundStatus;

  @JsonProperty("refundReceiveAccount")
  private String refundReceiveAccount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Receipt {
  @JsonProperty("url")
  private String url;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Checkout {
  @JsonProperty("url")
  private String url;
}
