package com.grayseal.transactapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AccountReqDTO {

    @NotEmpty(message = "Account id must be provided")
    private String id;
    @NotEmpty(message = "Account number is required")
    private String accountNumber;
    @NotEmpty(message = "Balance is required")
    private String balance;
    @NotEmpty(message = "Customer id is required")
    private String customerId;
}