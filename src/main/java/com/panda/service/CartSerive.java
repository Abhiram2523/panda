package com.panda.service;

import com.panda.Exception.CartException;
import com.panda.Exception.CartItemException;
import com.panda.Exception.FoodException;
import com.panda.Exception.UserException;
import com.panda.model.Cart;
import com.panda.model.CartItem;
import com.panda.model.Food;
import com.panda.model.User;
import com.panda.request.AddCartItemRequest;
import com.panda.request.UpdateCartItemRequest;

public interface CartSerive {

	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws UserException, FoodException, CartException, CartItemException;

	public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws CartItemException;

	public Cart removeItemFromCart(Long cartItemId, String jwt) throws UserException, CartException, CartItemException;

	public Long calculateCartTotals(Cart cart) throws UserException;
	
	public Cart findCartById(Long id) throws CartException;
	
	public Cart findCartByUserId(Long userId) throws CartException, UserException;
	
	public Cart clearCart(Long userId) throws CartException, UserException;
	

	

}
