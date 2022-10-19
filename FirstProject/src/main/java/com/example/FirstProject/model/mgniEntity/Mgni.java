package com.example.FirstProject.model.mgniEntity;

import com.example.FirstProject.model.cashiEntity.Cashi;
import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="mgn_mgni")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Mgni {


    @Id
    @Column(name="mgni_id")
    private String mgniId;


    @Column(name="mgni_time")
    //@XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDateTime mgniTime;

    @Column(name="mgni_type")
    private String mgniType;

    @Column(name="mgni_cm_no")
    private String mgniCmNo;

    @Column(name="mgni_kac_type")
    private String mgniKacType;

    @Column(name="mgni_bank_no")
    private String mgniBankNo;

    @Column(name="mgni_ccy")
    private String mgniCcy;

    @Column(name="mgni_pv_type")
    private String mgniPvType;

    @Column(name="mgni_bicacc_no")
    private String mgniBicaccNo;

    @Column(name="mgni_i_type")
    private String mgniIType;

    @Column(name="mgni_p_reason")
    private String mgniPReason;

    @Column(name="mgni_amt")
    private double mgniAmt;

    @Column(name="mgni_ct_name")
    private String mgniCtName;

    @Column(name="mgni_ct_tel")
    private String mgniCtTel;

    @Column(name="mgni_status")
    private String mgniStatus;

    @Column(name="mgni_u_time")
    private LocalDateTime mgniUTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashi_mgni_id")
    private List<Cashi> cashis;
}


class myKey implements Serializable {
    private String mgniId;
}
