package com.example.FirstProject.dto.request;


import lombok.*;

import javax.validation.constraints.Min;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conditions {


    private String startDate="";
    private String endDate="";
    private String bankno="";
    private String ccy="";
    private String cmno="";
    private String itype="";
    @Min(1)
    private int page;
    @Min(1)
    private int size;
}
