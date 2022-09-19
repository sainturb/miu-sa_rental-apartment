package miu.edu.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PlaceOrderDTO {
    private Map<String, Object> address;
    private Map<String, Object> paymentInfo;
    private List<ItemDTO> items;
}
