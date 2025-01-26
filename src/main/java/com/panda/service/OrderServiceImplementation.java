package com.panda.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.panda.model.*;
import com.panda.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.panda.Exception.CartException;
import com.panda.Exception.OrderException;
import com.panda.Exception.RestaurantException;
import com.panda.Exception.UserException;
import com.panda.request.CreateOrderRequest;
import com.razorpay.RazorpayException;
@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private BusDetailsRepository busDetailsRepository;
	@Autowired
	private CartSerive cartService;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private NotificationService notificationService;

	@Override
	@Transactional
	public PaymentResponse createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException, RazorpayException {
		BusDetails shippingAddress = order.getBusDetails();

		// Save BusDetails and ensure it gets an ID
		BusDetails savedAddress = busDetailsRepository.save(shippingAddress);

		// Update user's address list is removed as we no longer keep track of addresses in User entity

		Optional<Restaurant> restaurantOpt = restaurantRepository.findById(order.getRestaurantId());
		if (restaurantOpt.isEmpty()) {
			throw new RestaurantException("Restaurant not found with id " + order.getRestaurantId());
		}
		Restaurant restaurant = restaurantOpt.get();

		Order createdOrder = new Order();
		createdOrder.setCustomer(user);
		createdOrder.setBusDetails(savedAddress);
		createdOrder.setCreatedAt(new Date());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.setRestaurant(restaurant);

		Cart cart = cartService.findCartByUserId(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cart.getItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getFood().getPrice() * cartItem.getQuantity());
			orderItems.add(orderItemRepository.save(orderItem));
		}

		createdOrder.setTotalAmount(cartService.calculateCartTotals(cart));
		createdOrder.setItems(orderItems);

		Order savedOrder = orderRepository.save(createdOrder);
		restaurant.getOrders().add(savedOrder);
		restaurantRepository.save(restaurant);

		return paymentService.generatePaymentLink(savedOrder);
	}





	@Override
	public void cancelOrder(Long orderId) throws OrderException {
           Order order =findOrderById(orderId);
           if(order==null) {
        	   throw new OrderException("Order not found with the id "+orderId);
           }

		   orderRepository.deleteById(orderId);

	}

	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> order = orderRepository.findById(orderId);
		if(order.isPresent()) return order.get();

		throw new OrderException("Order not found with the id "+orderId);
	}

	@Override
	public List<Order> getUserOrders(Long userId) throws OrderException {
		List<Order> orders=orderRepository.findAllUserOrders(userId);
		return orders;
	}

	@Override
	public List<Order> getOrdersOfRestaurant(Long restaurantId,String orderStatus) throws OrderException, RestaurantException {

			List<Order> orders = orderRepository.findOrdersByRestaurantId(restaurantId);

			if(orderStatus!=null) {
				orders = orders.stream()
						.filter(order->order.getOrderStatus().equals(orderStatus))
						.collect(Collectors.toList());
			}

			return orders;
	}
//    private List<MenuItem> filterByVegetarian(List<MenuItem> menuItems, boolean isVegetarian) {
//    return menuItems.stream()
//            .filter(menuItem -> menuItem.isVegetarian() == isVegetarian)
//            .collect(Collectors.toList());
//}



	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws OrderException {
		Order order=findOrderById(orderId);

		System.out.println("--------- "+orderStatus);

		if(orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED")
				|| orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
			order.setOrderStatus(orderStatus);
			Notification notification=notificationService.sendOrderStatusNotification(order);
			return orderRepository.save(order);
		}
		else throw new OrderException("Please Select A Valid Order Status");


	}



}
