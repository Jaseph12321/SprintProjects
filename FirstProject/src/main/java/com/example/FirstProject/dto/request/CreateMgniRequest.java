package com.example.FirstProject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMgniRequest {

    @NotEmpty(message = "不能為空")
    private String mgniType;

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=\\d)]{7}", message = "格式錯誤")
    private String mgniCmNo;

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=1-2]", message = "格式錯誤")
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
    @Pattern(regexp = "^[(?=\\d)]{21}", message = "格式錯誤")
    private String mgniBicaccNo;


    private String mgniPReason;

    private double mgniAmt;

    @NotBlank(message = "don't blank")
    private String mgniCtName;

    @NotBlank
    @Pattern(regexp = "^[(?=\\d)]{1,30}", message = "格式錯誤")
    private String mgniCtTel;

    @NotBlank
    @Pattern(regexp = "^[(?=[0-7])]{1}", message = "格式錯誤")
    private String mgniStatus;

    private List<CashiRequest> cashi;

    //private List<Cashi> cashi;
}
