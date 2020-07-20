package com.cmbchina.o2o.wd.onlinemarket.feign;


import com.cmbchina.o2o.wd.onlinemarket.command.account.RegisterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;

@Component
@FeignClient("oauth2-server")
public interface AccountFeign {

    @PostMapping("/oauth/token")
    Object obtainToken(@RequestParam("client_id") @NotEmpty String client_id,
                       @RequestParam("client_secret") @NotEmpty String client_secret,
                       @RequestParam("grant_type") @NotEmpty String grant_type,
                       @RequestParam("username") @NotEmpty String username,
                       @RequestParam("password") @NotEmpty String password);

    @PostMapping("/oauth/register")
    Result register(@RequestBody RegisterCommand command);

    @GetMapping("/oauth/user")
    Result user(@RequestParam("access_token") String token);
}
