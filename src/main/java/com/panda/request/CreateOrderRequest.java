package com.panda.request;

import com.panda.model.Address;

import com.panda.model.BusDetails;
import lombok.Data;

@Data
public class CreateOrderRequest {
 
	private Long restaurantId;
	
	private BusDetails busDetails;
	
    
}
