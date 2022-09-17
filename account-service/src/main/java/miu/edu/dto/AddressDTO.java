package miu.edu.dto;

import lombok.Data;
import miu.edu.model.User;

import javax.persistence.*;

@Data
public class AddressDTO {
    private Long id;
    private String address;
    private String address1;
    private String state;
    private String street;
    private String zipcode;
    private UserDTO user;
}
