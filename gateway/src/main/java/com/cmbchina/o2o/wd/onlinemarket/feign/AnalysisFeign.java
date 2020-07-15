package com.cmbchina.o2o.wd.onlinemarket.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "analysis-server")
public interface AnalysisFeign {

    @GetMapping("/getUser")
    Object getUser();

}