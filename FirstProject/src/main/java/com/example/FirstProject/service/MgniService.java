package com.example.FirstProject.service;

import com.example.FirstProject.dto.request.*;
import com.example.FirstProject.dto.response.MgniResponse;
import com.example.FirstProject.dto.response.StatusResponse;
import com.example.FirstProject.exception.DataNotFoundException;
import com.example.FirstProject.model.cashiEntity.Cashi;
import com.example.FirstProject.model.cashiEntity.CashiRepository;
import com.example.FirstProject.model.mgniEntity.Mgni;
import com.example.FirstProject.model.mgniEntity.MgniPage;
import com.example.FirstProject.model.mgniEntity.MgniRepository;
import com.example.FirstProject.model.mgniEntity.MgniSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MgniService {

    @Autowired
    MgniRepository mgniRepository;

    @Autowired
    CashiService cashiService;


    @Autowired
    MgniPage mgniPage;

    @Autowired
    CashiRepository cashiRepository;


    //get all(page)

    public Page<Mgni> getPageAll(Conditions conditions){
        return this.mgniPage.thePage(conditions);
    }
    // get all
    public List<MgniResponse> getAll() throws DataNotFoundException {
        List<Mgni> mgniList = this.mgniRepository.findAll();
        if (mgniList.size() == 0) throw new DataNotFoundException("data not found");

        return setListResponse(mgniList);
    }

    public List<MgniResponse> getBetween(String startdate, String enddate) throws DataNotFoundException, ParseException {
        setDateFormat(startdate);
        setDateFormat(enddate);
        LocalDate l = LocalDate.parse(startdate);
        LocalDateTime d1 = l.atStartOfDay();
        LocalDate l2 = LocalDate.parse(enddate);
        LocalDateTime d2 = l2.atTime(23, 59, 59);

        List<Mgni> mgniList = this.mgniRepository.findAll(MgniSpecifications.betweenTime(d1, d2));

        if (mgniList.size() == 0) throw new DataNotFoundException("data not found");


        return setListResponse(mgniList);
    }


    //get one data by id
    public MgniResponse getOneById(String id) throws DataNotFoundException {
        if (this.mgniRepository.findByMgniId(id) == null)
            throw new DataNotFoundException("資料不存在");
        return setResponse(this.mgniRepository.findByMgniId(id));
    }


    // find by time with block
    public List<MgniResponse> findConditions(String ccy, String bankno, String cmno, String type) throws DataNotFoundException {

        List<Mgni> mgniList = this.mgniRepository.findAll();
        List<Mgni> newList = new ArrayList<Mgni>();

        for (Mgni mgni : mgniList) {
            int flag = 0;
            conditions(type, mgni, newList, flag);
            conditions(bankno, mgni, newList, flag);
            conditions(ccy, mgni, newList, flag);
            conditions(cmno, mgni, newList, flag);
        }

        if (newList.size() == 0) throw new DataNotFoundException("data not found");

        return setListResponse(newList);
    }

    //create mgni
    public StatusResponse createMgni(CreateMgniRequest request) {
        Mgni m = new Mgni();
        Cashi c = new Cashi();
        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println(request.getCashi().get(0).getCashiAccNo());
        m.setMgniId(createMgniId(dateTime));
        m.setMgniTime(dateTime);
        m.setMgniType(request.getMgniType());
        m.setMgniCmNo(request.getMgniCmNo());
        m.setMgniKacType(request.getMgniKacType());
        m.setMgniBankNo(request.getMgniBankNo());
        m.setMgniCcy(request.getMgniCcy());
        m.setMgniPvType(request.getMgniPvType());
        m.setMgniBicaccNo(request.getMgniBicaccNo());
        m.setMgniPReason(request.getMgniPReason());
        m.setMgniAmt(request.getMgniAmt());
        m.setMgniCtName(request.getMgniCtName());
        m.setMgniCtTel(request.getMgniCtTel());
        m.setMgniStatus(request.getMgniStatus());
        m.setMgniUTime(dateTime);
//        for(Cashi cashi : request.getCashi()){
//            cashi.setCashiMgniId(createMgniId(dateTime));
//        }
        this.mgniRepository.save(m);

        for (CashiRequest r : request.getCashi())
            cashiService.createCashi(r);

        return new StatusResponse("創建成功");
    }

    public StatusResponse createJiaoGe(CreateJiaoGeRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        Mgni m = Mgni.builder()
                .mgniId("MGI" + createMgniId(dateTime))
                .mgniTime(dateTime)
                .mgniType("1")
                .mgniCmNo(request.getCnNo())
                .mgniKacType(request.getKacType())
                .mgniBankNo(request.getBankNo())
                .mgniCcy(request.getCcy())
                .mgniPvType(request.getPvType())
                .mgniBicaccNo(request.getBicaccNo())
                .mgniIType(request.getItype())
                .mgniPReason(request.getPReason())
                .mgniAmt(request.getAmt())
                .mgniCtName(request.getCtName())
                .mgniCtTel(request.getCtTel())
                .mgniStatus(request.getStatus())
                .mgniUTime(dateTime)
                .build();
        return new StatusResponse("jiaoge create success");
    }

    //update mgni
    public StatusResponse updateMgni(String id,UpdateMgniRequest request) {
        Mgni m = this.mgniRepository.findByMgniId(id);
        LocalDateTime dateTime = LocalDateTime.now();

        m.setMgniKacType(request.getMgniKacType());
        m.setMgniBankNo(request.getMgniBankNo());
        m.setMgniCcy(request.getMgniCcy());
        m.setMgniPvType(request.getMgniPvType());
        m.setMgniPReason(request.getMgniPReason());
        m.setMgniAmt(request.getMgniAmt());
        m.setMgniCtName(request.getMgniCtName());
        m.setMgniCtTel(request.getMgniCtTel());
        m.setMgniStatus(request.getMgniStatus());
        m.setMgniUTime(dateTime);
        this.mgniRepository.save(m);


        return new StatusResponse("更新成功");
    }
    //change mgni status

    public StatusResponse changeStatus(CreateMgniRequest request) {
        Mgni m = new Mgni();
        LocalDateTime dateTime = LocalDateTime.now();

        m.setMgniStatus(request.getMgniStatus());
        m.setMgniUTime(dateTime);
        this.mgniRepository.save(m);

        return new StatusResponse("更改狀態成功");
    }

    //below are methods
    public List<MgniResponse> setListResponse(List<Mgni> list) {
        List<MgniResponse> results = new ArrayList<>();
        for (Mgni m : list) {
            results.add(setResponse(m));
        }

        return results;
    }

    public MgniResponse setResponse(Mgni mgni) {
        MgniResponse response = new MgniResponse();
          response.setMgni(mgni);
        return response;
    }

    //create mgni Id
    public String createMgniId(LocalDateTime t) {
        String s = t.toString();
        String date = s.substring(0, 10);
        String time = s.substring(11, 23);
        String[] split1 = date.split("-");
        String[] split2 = time.split(":");
        System.out.println(split2[0]);
        String s3 = split2[2];
        String a = s3.substring(0, 2);
        String b = s3.substring(3);

        StringBuilder combine = new StringBuilder();
        for (String i : split1) combine.append(i);
        for (int i = 0; i < split2.length - 1; i++) combine.append(split2[i]);
        combine.append(a).append(b);

        return "MGI" + combine.toString();
    }

    public void setDateFormat(String dateFormat) throws ParseException {
        StringBuilder s = new StringBuilder(dateFormat);
        System.out.println(s);
        s.insert(4,"-");
        System.out.println(s);
        s.insert(7,"-");
    }

    public <T> void conditions(T condition, Mgni mgni, List<Mgni> newList, int flag) {
        if (condition != null) {
            if (flag == 0) {

                if (Objects.equals(mgni.getMgniIType(), condition)) {
                    newList.add(mgni);
                    flag = 1;
                }
            } else if (flag == 1) {
                if (!Objects.equals(mgni.getMgniIType(), condition)) {
                    newList.remove(mgni);
                    flag =0;
                }
            }
        }
    }



}



