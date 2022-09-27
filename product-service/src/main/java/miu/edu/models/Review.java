package miu.edu.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName = "review")
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private double rating;
    private String message;
    @CreatedBy
    private String writtenBy;
    @CreatedDate
    private Instant writtenDate;
    private Long productId;
}
