package com.panda.service;

import com.panda.model.Order;
import com.panda.model.PaymentResponse;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImplementation implements PaymentService {

	@Value("${razorpay.api.key}")
	private String razorpayKey;

	@Value("${razorpay.api.secret}")
	private String razorpaySecret;

	@Override
	public PaymentResponse generatePaymentLink(Order order) throws RazorpayException {
		RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);

		Map<String, Object> options = new HashMap<>();
		options.put("amount", order.getTotalAmount() * 100); // Amount in paise
		options.put("currency", "INR");
		options.put("receipt", "receipt#" + order.getId());

		// Convert the options Map to a JSONObject
		JSONObject orderRequest = new JSONObject(options);

		com.razorpay.Order razorpayOrder = client.orders.create(orderRequest);

		PaymentResponse res = new PaymentResponse();
		res.setOrderId(razorpayOrder.get("id"));
		res.setAmount(razorpayOrder.get("amount"));
		res.setCurrency(razorpayOrder.get("currency"));
		res.setKey(razorpayKey);

		return res;
	}
}

