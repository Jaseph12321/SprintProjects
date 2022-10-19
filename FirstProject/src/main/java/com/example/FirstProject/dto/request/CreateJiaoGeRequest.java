package com.example.FirstProject.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJiaoGeRequest {

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=\\d)]{7}", message = "格式錯誤")
    private String cnNo;


    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=\\d)]{1}", message = "格式錯誤")
    private String kacType;


    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=\\d)]{3}", message = "格式錯誤")
    private String bankNo;

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=TWD|USD)]{3}", message = "格式錯誤")
    private String ccy;


    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=1-2)]", message = "格式錯誤")
    private String pvType;


    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=\\d)]{21}", message = "格式錯誤")
    private String bicaccNo;

    @NotEmpty(message = "不能為空")
    @Pattern(regexp = "^[(?=1-4)]", message = "格式錯誤")
    private String Itype;

    private String pReason;
    private double amt;

    private String ctName;


    @NotBlank(message = "no blank")
    @Pattern(regexp = "^[(?=\\d)]{1,30}", message = "格式錯誤")
    private String ctTel;


    @NotBlank(message = "no blank")
    @Pattern(regexp = "^[(?=0-7)]", message = "格式錯誤")
    private String status;
}
