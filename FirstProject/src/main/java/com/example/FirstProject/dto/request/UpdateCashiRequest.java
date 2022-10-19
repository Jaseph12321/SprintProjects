package com.example.FirstProject.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCashiRequest {

    @NotEmpty(message = "不能為空")
    private String cashiAccNo;

    @NotEmpty(message = "不能為空")
    private String cashiCcy;

    @NotEmpty(message = "不能為空")
    private double cashiAmt;
}
