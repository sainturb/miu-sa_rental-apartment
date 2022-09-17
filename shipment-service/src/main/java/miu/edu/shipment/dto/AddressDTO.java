package miu.edu.shipment.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String address;
    private String address1;
    private String state;
    private String zipcode;
}
