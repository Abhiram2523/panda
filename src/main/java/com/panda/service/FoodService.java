package com.panda.service;

import java.util.List;
import com.panda.Exception.FoodException;
import com.panda.Exception.RestaurantException;
import com.panda.model.Category;
import com.panda.model.Food;
import com.panda.model.Restaurant;
import com.panda.request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req,Category category,
						   Restaurant restaurant) throws FoodException, RestaurantException;

	void deleteFood(Long foodId) throws FoodException;

	public List<Food> getRestaurantsFood(Long restaurantId,
										 boolean isVegetarian, boolean isNonveg, boolean isSeasonal,String foodCategory) throws FoodException;

	public List<Food> searchFood(String keyword);

	public Food findFoodById(Long foodId) throws FoodException;

	public Food updateAvailibilityStatus(Long foodId) throws FoodException;
}