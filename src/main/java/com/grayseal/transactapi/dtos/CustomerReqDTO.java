package com.grayseal.transactapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;


@Data
public class CustomerReqDTO {

    @NotEmpty(message = "Customer id must be provided")
    private String id;
    @NotEmpty(message = "Customer name is required")
    private String name;
    @Email
    @NotEmpty(message = "Customer email is required")
    private String email;
    @NotEmpty(message = "Customer phone number is required")
    private String phone;
    @NotEmpty(message = "Password is required")
    private String password;
    private List<AccountReqDTO> accounts;
}
