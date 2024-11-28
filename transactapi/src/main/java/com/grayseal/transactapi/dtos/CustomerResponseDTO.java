package com.grayseal.transactapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerResponseDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
}
