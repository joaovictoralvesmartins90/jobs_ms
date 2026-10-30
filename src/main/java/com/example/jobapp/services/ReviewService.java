package com.example.jobapp.services;

import com.example.jobapp.models.Company;
import com.example.jobapp.models.Review;
import com.example.jobapp.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService{

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewService(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> findAll(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Optional<Review> findById(Long companyId, Long reviewId) {
        return reviewRepository.findByCompanyId(companyId)
                .stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst();
    }

    @Override
    public Review addReview(Review review, Long companyId) {
        Company company = companyService.findCompanyById(companyId).get();
        review.setCompany(company);
        return reviewRepository.save(review);
    }


    @Override
    public boolean deleteReview(Long reviewId, Long companyId) {
        Optional<Company> company = companyService.findCompanyById(companyId);
        if(company.isEmpty()){
            return false;
        }
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()){
            return false;
        }
        reviewRepository.delete(review.get());
        return true;
    }

    @Override
    public boolean updateReview(Review review, Long companyId, Long reviewId) {
        Optional<Company> company = companyService.findCompanyById(companyId);
        if(company.isEmpty()){
            return false;
        }
        Optional<Review> reviewDb = reviewRepository.findById(reviewId);
        if (reviewDb.isEmpty()){
            return false;
        }
        Review reviewUpdate = reviewDb.get();
        reviewUpdate.setTitle(review.getTitle());
        reviewUpdate.setDescription(review.getTitle());
        reviewUpdate.setRating(review.getRating());
        reviewUpdate.setCompany(company.get());
        reviewRepository.save(reviewUpdate);
        return true;
    }
}
