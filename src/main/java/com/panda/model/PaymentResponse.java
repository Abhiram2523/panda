package com.panda.model;

import lombok.Data;

@Data
public class PaymentResponse {
	private String orderId;
	private String currency;
	private int amount;
	private String key;
}
