package com.example.jobapp.services;

import com.example.jobapp.models.Review;

import java.util.List;
import java.util.Optional;

public interface IReviewService {
    List<Review> findAll(Long companyId);
    Optional<Review> findById(Long companyId, Long reviewId);
    Review addReview(Review review, Long companyId);
    boolean deleteReview(Long reviewId, Long companyId);
    boolean updateReview(Review review, Long companyId, Long reviewId);
}
