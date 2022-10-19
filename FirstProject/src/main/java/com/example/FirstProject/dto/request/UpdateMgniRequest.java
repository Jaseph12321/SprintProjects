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
public class UpdateMgniRequest {

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=1|2]}", message = "格式錯誤")
    private String mgniKacType;

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=\\d)]{3}", message = "格式錯誤")
    private String mgniBankNo;

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=TWD|USD)]{3}", message = "格式錯誤")
    private String mgniCcy;

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=1|2)]", message = "格式錯誤")
    private String mgniPvType;

    @NotEmpty(message = "不能為空")
    private String mgniPReason;


    private double mgniAmt;

    private String mgniCtName;

    @Pattern(regexp = "^[(?=\\d)]{1,30}", message = "格式錯誤")
    private String mgniCtTel;

    @Pattern(regexp = "^[(?=[0-7])]", message = "格式錯誤")
    private String mgniStatus;
}
