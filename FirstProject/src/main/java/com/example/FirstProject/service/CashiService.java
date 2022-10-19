package com.example.FirstProject.service;

import com.example.FirstProject.dto.request.CashiRequest;
import com.example.FirstProject.dto.request.UpdateCashiRequest;
import com.example.FirstProject.dto.response.CashiResponse;
import com.example.FirstProject.dto.response.StatusResponse;
import com.example.FirstProject.exception.DataNotFoundException;
import com.example.FirstProject.model.cashiEntity.Cashi;
import com.example.FirstProject.model.cashiEntity.CashiRepository;
import com.example.FirstProject.model.cashiEntity.CashiSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CashiService {

    @Autowired
    private CashiRepository cashiRepository;

    // get all
    public List<CashiResponse> getAll() throws DataNotFoundException {
        if(this.cashiRepository.findAll().size() == 0)
            throw new DataNotFoundException("not found");

        return setListResponse(this.cashiRepository.findAll());
    }

    public List<CashiResponse> getByYear(String year){
        List<Cashi> cashiList = this.cashiRepository.findAll(CashiSpecifications.likeYear(year));

        return setListResponse(cashiList);
    }
    //get one data by id
    public List<CashiResponse> getOneCashiById(String id) throws DataNotFoundException {
        if(this.cashiRepository.findByCashiMgniId(id)==null)
            throw new DataNotFoundException("not found");

        return setListResponse(this.cashiRepository.findByCashiMgniId(id));
    }
    // find by time with block


    //create cashi
    public StatusResponse createCashi(CashiRequest request){
        Cashi c = new Cashi();
        LocalDateTime dateTime= LocalDateTime.now();
        c.setCashiMgniId(createMgniId(dateTime));
        c.setCashiAccNo(request.getCashiAccNo());
        c.setCashiCcy(request.getCashiCcy());
        c.setCashiAmt(request.getCashiAmt());
        this.cashiRepository.save(c);
//          LocalDateTime dateTime= LocalDateTime.now();
//          Cashi c = Cashi.builder()
//                  .cashiMgniId(createMgniId(dateTime))
//                  .cashiAccNo(request.getCashiAccNo())
//                  .cashiCcy(request.getCashiCcy())
//                  .cashiAmt(request.getCashiAmt())
//                  .build();

        return new StatusResponse("add success");
    }
    //update cashi
    public StatusResponse updateCashi(UpdateCashiRequest request){
        Cashi c = new Cashi();
        LocalDateTime dateTime= LocalDateTime.now();
        c.setCashiMgniId(createMgniId(dateTime));
        c.setCashiAccNo(request.getCashiAccNo());
        c.setCashiCcy(request.getCashiCcy());
        c.setCashiAmt(request.getCashiAmt());
        cashiRepository.save(c);

        return new StatusResponse("update success");
    }

    //delete cashi
    public StatusResponse deleteCashi(String cashiId){
        this.cashiRepository.deleteByCashiMgniId(cashiId);

        return new StatusResponse("delete success");
    }
    //below are methods
    public List<CashiResponse> setListResponse(List<Cashi> list) {
        List<CashiResponse> results = new ArrayList<>();
        for (Cashi m : list)
        {
            results.add(setResponse(m));
        }
        return results;
    }

    public CashiResponse setResponse(Cashi cashi) {
        CashiResponse response = new CashiResponse();
        response.setCashiMgniId(cashi.getCashiMgniId());
        response.setCashiAccNo(cashi.getCashiAccNo());
        response.setCashiCcy(cashi.getCashiCcy());
        response.setCashiAmt(cashi.getCashiAmt());


        return response;
    }


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

}

