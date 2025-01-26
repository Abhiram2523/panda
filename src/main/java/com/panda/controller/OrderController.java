package com.panda.controller;

import java.util.List;

import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.panda.Exception.CartException;
import com.panda.Exception.OrderException;
import com.panda.Exception.RestaurantException;
import com.panda.Exception.UserException;
import com.panda.model.Order;
import com.panda.model.PaymentResponse;
import com.panda.model.User;
import com.panda.request.CreateOrderRequest;
import com.panda.service.OrderService;
import com.panda.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	@PostMapping("/order")
	public ResponseEntity<PaymentResponse> createOrder(
			@RequestBody CreateOrderRequest order,
			@RequestHeader("Authorization") String jwt)
			throws UserException, RestaurantException, CartException, RazorpayException, OrderException {
		User user = userService.findUserProfileByJwt(jwt);
		if (order != null) {
			System.out.println("order controller"+order+user);
			PaymentResponse res = orderService.createOrder(order, user);
			return ResponseEntity.ok(res);
		} else {
			throw new OrderException("Please provide valid request body");
		}
	}

	@GetMapping("/order/user")
    public ResponseEntity<List<Order>> getAllUserOrders(@RequestHeader("Authorization") String jwt) throws OrderException, UserException{

    	User user=userService.findUserProfileByJwt(jwt);

    	if(user.getId()!=null) {
    	List<Order> userOrders = orderService.getUserOrders(user.getId());
    	return ResponseEntity.ok(userOrders);
    	}else {
    		return new ResponseEntity<List<Order>>(HttpStatus.BAD_REQUEST);
    	}
    }





}
