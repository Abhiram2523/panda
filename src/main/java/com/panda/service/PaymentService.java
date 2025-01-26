package com.panda.service;

import com.panda.model.Order;
import com.panda.model.PaymentResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {
	public PaymentResponse generatePaymentLink(Order order) throws RazorpayException;
}


