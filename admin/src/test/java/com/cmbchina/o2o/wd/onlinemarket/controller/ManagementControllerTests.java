package com.cmbchina.o2o.wd.onlinemarket.controller;

import com.cmbchina.o2o.wd.onlinemarket.command.account.UserFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.mapper.AddressMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.ManagementService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@Slf4j
@WebMvcTest(value = ManagementController.class)
public class ManagementControllerTests {
    private static final String TOKEN = "F-os-AH3hfoUG0x1MN-Z96gYBTU";

    @Autowired
    private MockMvc mockMvc;

    // @MockBean
    // private MockHttpServletRequest request;
    @MockBean
    private ManagementService managementService;

    @MockBean
    private AddressMapper addressMapper;

    @Test
    public void getOrderList() throws Exception{
        MockHttpServletRequest request = new MockHttpServletRequest();
        UserFilterCommand command = new UserFilterCommand();
        Mockito.when(managementService.getUserList(command,request)).thenReturn(PageResult.success());
        RequestBuilder builder = MockMvcRequestBuilders.get("/order/list").param("access_token",TOKEN);
        MvcResult result = mockMvc.perform(builder).andReturn();
        log.info(result.getResponse().getContentAsString());
        // Assert.assertEquals(true,reuslt.isSuccess());
    }
}
