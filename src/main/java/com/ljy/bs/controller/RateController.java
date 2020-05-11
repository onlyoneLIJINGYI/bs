package com.ljy.bs.controller;

import com.ljy.bs.entity.Rate;
import com.ljy.bs.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RateController {
    @Autowired
    RateService rateService;

   /*
   //请求查询资源(根据资源id)含有的评分
   @CrossOrigin //允许跨域请求的注解
    @GetMapping("/api/resource/{rid}/rate")
    public List<Rate> rateByRid(@PathVariable("rid") int rid) throws Exception {
//        return ResultFactory.buildSuccessResult(RateService.listAllByRid(rid));
        return rateService.listAllByRid(rid);
    }*/
}
