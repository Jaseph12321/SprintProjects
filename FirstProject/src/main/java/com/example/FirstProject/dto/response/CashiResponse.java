package com.example.FirstProject.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashiResponse {
    private String cashiMgniId;
    private String cashiAccNo;
    private String cashiCcy;
    private double cashiAmt;
}
