package miu.edu.product.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@org.springframework.data.elasticsearch.annotations.Document(indexName = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int totalOccupancy;
    private int totalBedrooms;
    private int totalBathrooms;
    private String homeType;
    @Column(length = 500)
    private String summary;
    private String address;
    private boolean hasTv = false;
    private boolean hasKitchen = false;
    private boolean hasAirCon = false;
    private boolean hasHeating = false;
    private boolean hasInternet = false;
    private double price;
    private Long ownerId;
    private double latitude;
    private double longitude;
    private LocalDate availableFrom;
    private LocalDate availableUntil;
    private double rating = 5;
}
