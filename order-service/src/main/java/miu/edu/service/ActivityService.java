package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.model.Activity;
import miu.edu.model.Order;
import miu.edu.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository repository;

    public void save(Order order, String prevStatus) {
        Activity activity = new Activity();
        activity.setOrderNumber(order.getOrderNumber());
        activity.setActivity(String.format("Status change from %s to %s", prevStatus, order.getStatus()));
        activity.setCreatedDate(Instant.now());
        activity.setUserId(order.getUserId());
        if (Objects.nonNull(order.getNote())) {
            activity.setNote(order.getNote());
        }
        repository.save(activity);
    }

    public List<Activity> getByOrderNumber(String orderNumber, Long userId) {
        return repository.findByOrderNumberAndUserId(orderNumber, userId);
    }
}
