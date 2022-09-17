package miu.edu.shipment.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ShipmentDTO {
    private List<Map<String, Object>> items;
    private AddressDTO address;
}
