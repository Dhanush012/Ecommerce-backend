package com.ridhi.order.Dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String country;
    private String state;
}
