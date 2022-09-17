package miu.edu.payment;

import lombok.*;

@Data
public class AddressDTO {
    private Long id;
    private String address;
    private String address1;
    private String state;
    private String street;
    private String zipcode;
}
