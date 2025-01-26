package com.panda.service;

import java.util.List;


import com.panda.Exception.CartException;
import com.panda.Exception.OrderException;
import com.panda.Exception.RestaurantException;
import com.panda.Exception.UserException;
import com.panda.model.Order;
import com.panda.model.PaymentResponse;
import com.panda.model.User;
import com.panda.request.CreateOrderRequest;
import com.razorpay.RazorpayException;

public interface OrderService {
	
	 public PaymentResponse createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException, RazorpayException;
	 
	 public Order updateOrder(Long orderId, String orderStatus) throws OrderException;
	 
	 public void cancelOrder(Long orderId) throws OrderException;
	 
	 public List<Order> getUserOrders(Long userId) throws OrderException;
	 
	 public List<Order> getOrdersOfRestaurant(Long restaurantId,String orderStatus) throws OrderException, RestaurantException;
	 

}
