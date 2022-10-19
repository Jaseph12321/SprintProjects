package com.example.FirstProject.model.cashiEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mgn_cashi")
@IdClass(myKey.class)
public class Cashi {

    @Id
    @Column(name = "cashi_mgni_id")
    private String cashiMgniId;

    @Id
    @Column(name = "cashi_acc_no")
    private String cashiAccNo;

    @Id
    @Column(name = "cashi_ccy")
    private String cashiCcy;

    @Column(name = "cashi_amt")
    private double cashiAmt;

}

class myKey implements Serializable {
    private String cashiMgniId;
    private String cashiAccNo;
    private String cashiCcy;
}