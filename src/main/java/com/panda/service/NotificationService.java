package com.panda.service;

import java.util.List;

import com.panda.model.Notification;
import com.panda.model.Order;
import com.panda.model.Restaurant;
import com.panda.model.User;

public interface NotificationService {
	
	public Notification sendOrderStatusNotification(Order order);
	public void sendRestaurantNotification(Restaurant restaurant, String message);
	public void sendPromotionalNotification(User user, String message);
	
	public List<Notification> findUsersNotification(Long userId);

}
