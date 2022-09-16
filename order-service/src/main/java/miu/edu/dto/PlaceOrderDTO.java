package miu.edu.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PlaceOrderDTO {
    List<Map<String, Object>> items;
}
