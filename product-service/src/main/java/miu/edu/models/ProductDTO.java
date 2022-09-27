package miu.edu.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    @JsonProperty("total_occupancy")
    private int totalOccupancy;
    @JsonProperty("total_bedrooms")
    private int totalBedrooms;
    @JsonProperty("total_bathrooms")
    private int totalBathrooms;
    @JsonProperty("home_type")
    private String homeType;
    private String summary;
    private String address;
    @JsonProperty("has_tv")
    private boolean hasTv;
    @JsonProperty("has_kitchen")
    private boolean hasKitchen;
    @JsonProperty("has_air_con")
    private boolean hasAirCon;
    @JsonProperty("has_heating")
    private boolean hasHeating;
    @JsonProperty("has_internet")
    private boolean hasInternet;
    private double price;
    @JsonProperty("owner_id")
    private Long ownerId;
    private double latitude;
    private double longitude;
    @JsonProperty("available_from")
    private String availableFrom;
    @JsonProperty("available_until")
    private String availableUntil;
}
