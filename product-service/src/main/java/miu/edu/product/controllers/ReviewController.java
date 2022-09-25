package miu.edu.product.controllers;

import miu.edu.product.models.Review;
import miu.edu.product.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("create/{productId}")
    public Review createReview(@PathVariable Long productId, @RequestBody Review review) {
        review.setProductId(productId);
        return reviewService.save(review);
    }
}
