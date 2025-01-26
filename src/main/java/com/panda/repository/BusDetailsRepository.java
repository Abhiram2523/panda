package com.panda.repository;
import com.panda.model.BusDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusDetailsRepository extends JpaRepository<BusDetails, Long> {
}
