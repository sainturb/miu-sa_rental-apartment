package miu.edu.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PlaceOrderDTO {
    private Map<String, Object> paymentInfo;
    private ItemDTO item;
}
