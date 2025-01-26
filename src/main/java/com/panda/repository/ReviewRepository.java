package com.panda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panda.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
