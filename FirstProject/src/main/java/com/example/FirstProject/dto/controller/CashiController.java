package com.example.FirstProject.dto.controller;

import com.example.FirstProject.dto.request.CashiRequest;
import com.example.FirstProject.dto.request.UpdateCashiRequest;
import com.example.FirstProject.dto.response.CashiResponse;
import com.example.FirstProject.dto.response.StatusResponse;
import com.example.FirstProject.exception.DataNotFoundException;
import com.example.FirstProject.service.CashiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cashi")
@Validated
public class CashiController {

    @Autowired
    CashiService cashiService;

    // get all
    @GetMapping()
    public List<CashiResponse> getAll() throws DataNotFoundException {
        return this.cashiService.getAll();
    }
    //get one data by id
    @GetMapping("/{id}")
    public List<CashiResponse> getOne(@PathVariable String id) throws DataNotFoundException {
        try{
            return this.cashiService.getOneCashiById(id);

        }catch(Exception e){
              e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/year")
    public List<CashiResponse> getByYear(@RequestParam@Pattern(regexp = "^[(?=\\d)]{4}", message = "年份 格式錯誤") String year) {
        try{
            return this.cashiService.getByYear(year);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //create cashi
    @PostMapping()
    public StatusResponse createCashi(@RequestBody @Valid CashiRequest request){
        try{
            return this.cashiService.createCashi(request);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //update cashi
    @PutMapping()
    public StatusResponse updateCashi(@RequestBody @Valid UpdateCashiRequest request){
        try{
            return this.cashiService.updateCashi(request);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //delete cashi
    @DeleteMapping("/{id}")
    public StatusResponse deleteCashi(@PathVariable String id){
        return this.cashiService.deleteCashi(id);
    }
}