package miu.edu.dto;

import lombok.Data;
import lombok.NonNull;
import miu.edu.model.Item;
import miu.edu.model.Order;

import java.util.List;
import java.util.Map;

@Data
public class PlaceOrderDTO {
    private Map<String, Object> address;
    private Map<String, Object> paymentInfo;
    @NonNull
    private List<ItemDTO> items;
}
