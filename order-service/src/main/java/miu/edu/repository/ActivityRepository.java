package miu.edu.repository;

import miu.edu.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByOrderNumberAndUserId(String orderNumber, Long userId);
}
