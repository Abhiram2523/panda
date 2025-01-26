package com.panda.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panda.domain.USER_ROLE;
import com.panda.dto.RestaurantDto;
import jakarta.persistence.*;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String fullName;
	private String email;
	private String password;

	private USER_ROLE role;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Order> orders;

	@ElementCollection
	private List<RestaurantDto> favorites = new ArrayList<>();

	private String status;
}