package com.panda.service;

import java.util.List;

import com.panda.Exception.ReviewException;
import com.panda.model.Review;
import com.panda.model.User;
import com.panda.request.ReviewRequest;

public interface ReviewSerive {
	
    public Review submitReview(ReviewRequest review,User user);
    public void deleteReview(Long reviewId) throws ReviewException;
    public double calculateAverageRating(List<Review> reviews);
}
