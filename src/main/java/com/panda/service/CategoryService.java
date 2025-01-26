package com.panda.service;

import java.util.List;
import com.panda.Exception.RestaurantException;
import com.panda.model.Category;

public interface CategoryService {

	public Category createCategory (String name,Long userId) throws RestaurantException;
	public List<Category> findCategoryByRestaurantId(Long restaurantId) throws RestaurantException;
	public Category findCategoryById(Long id) throws RestaurantException;

}


