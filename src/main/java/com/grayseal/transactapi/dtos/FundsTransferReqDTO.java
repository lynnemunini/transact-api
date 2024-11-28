package com.grayseal.transactapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FundsTransferReqDTO {

    @NotEmpty(message = "Amount is required")
    private String amount;
    @NotEmpty(message = "Source account is required")
    private String sourceAccountNumber;
    @NotEmpty(message = "Destination account is required")
    private String destinationAccountNumber;
}