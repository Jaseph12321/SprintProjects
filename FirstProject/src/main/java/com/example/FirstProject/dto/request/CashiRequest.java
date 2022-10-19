package com.example.FirstProject.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashiRequest {

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=\\d)]{7}", message = "格式錯誤")
    private String cashiAccNo;

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=TWD|USD)]{3}", message = "格式錯誤")
    private String cashiCcy;

    private double cashiAmt;
}
