package com.example.FirstProject.dto.controller;

import com.example.FirstProject.dto.request.Conditions;
import com.example.FirstProject.dto.request.CreateMgniRequest;
import com.example.FirstProject.dto.request.UpdateMgniRequest;
import com.example.FirstProject.dto.response.MgniResponse;
import com.example.FirstProject.dto.response.StatusResponse;
import com.example.FirstProject.exception.DataNotFoundException;
import com.example.FirstProject.exception.ParamInvalidException;
import com.example.FirstProject.model.mgniEntity.Mgni;
import com.example.FirstProject.service.MgniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mgni")
@Validated
public class MgniController {


    @Autowired
    MgniService mgniService;

    @GetMapping("/pages")
    public Page<Mgni> getWithPage(@RequestBody(required = false) Conditions conditions){
        System.out.println(conditions.getPage());
        return this.mgniService.getPageAll(conditions);
    }

    @GetMapping()
    public List<MgniResponse> getAll() throws DataNotFoundException {
        return this.mgniService.getAll();
    }

    //get one data by id
    @GetMapping("/{id}")
    public MgniResponse getOne(@PathVariable String id) throws DataNotFoundException {
        return this.mgniService.getOneById(id);
    }

    @GetMapping("/condition")
    public List<MgniResponse> getByCondition(
            @RequestParam(required = false)
            @Pattern(regexp = "^[(?=TWD|USD)]{3}", message = "幣別 格式錯誤")
            String ccy,
            @RequestParam(required = false)
            @Pattern(regexp = "^[(?=\\d)]{7}", message = "會員代號 格式錯誤")
            String cmno,
            @RequestParam(required = false)
            @Pattern(regexp = "^[(?=\\d)]{3}", message = "銀行代號 格式錯誤")
            String bankNo,
            @RequestParam(required = false)
            @Pattern(regexp = "^[(?=\\d)]{1}", message = "存入類別 格式錯誤")
            String itype) throws DataNotFoundException {
        return this.mgniService.findConditions(ccy, cmno, bankNo, itype);
    }

    // find by time with block
    @GetMapping("/betw")
    public List<MgniResponse> getBetween(
            @RequestParam @Pattern(regexp = "^[(?=\\d)]{8}", message = "日期 格式錯誤") String startdate,
            @RequestParam @Pattern(regexp = "^[(?=\\d)]{8}", message = "日期 格式錯誤") String enddate)
            throws Exception {
        try{
            List<String> ergs = new ArrayList<>();
            if(startdate ==null || enddate == null){
                ergs.add("兩者都必須同時輸入");
                throw new ParamInvalidException(ergs);
            }else if(Integer.parseInt(startdate)>Integer.parseInt(enddate)){
                ergs.add("起始日期不得大於結束日期");
                throw new ParamInvalidException(ergs);
            }
            return this.mgniService.getBetween(startdate, enddate);
        }catch(Exception e){
            e.printStackTrace();
            throw  new Exception(e);
        }
    }

    //create mgni
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public StatusResponse createMgni(@RequestBody @Valid CreateMgniRequest request) throws Exception {
        try{
            return this.mgniService.createMgni(request);

        }catch(Exception e){
            e.printStackTrace();
            throw  new Exception(e);
        }
    }


    //update mgni
    @PutMapping("/update/{id}")
    public StatusResponse updateMgni(@PathVariable String id,@RequestBody UpdateMgniRequest request) throws Exception {
        try{
            return this.mgniService.updateMgni(id,request);

        }catch(Exception e){
            e.printStackTrace();
            throw  new Exception(e);
        }
    }

}
