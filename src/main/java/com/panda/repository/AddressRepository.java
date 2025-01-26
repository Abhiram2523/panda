package com.panda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panda.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
