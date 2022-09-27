package miu.edu.repositories;

import miu.edu.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    long countByProductId(Long productId);

    List<Review> findAllByProductId(Long productId);
}
