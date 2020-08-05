package com.cmbchina.o2o.wd.onlinemarket.controller;


import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@Slf4j
@WebMvcTest(value = OrderController.class)
public class OrderControllerTests {

    private static final String TOKEN = "F-os-AH3hfoUG0x1MN-Z96gYBTU";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HttpServletRequest request;
    @MockBean
    private OrderService orderService;

    @Test
    public void getOrderList() throws Exception{
        OrderFilterCommand command = new OrderFilterCommand();
        Mockito.when(orderService.getOrderList(command,request)).thenReturn(PageResult.success());
        RequestBuilder builder = MockMvcRequestBuilders.get("/order/list").param("access_token",TOKEN);
        MvcResult result = mockMvc.perform(builder).andReturn();
        log.info(result.getResponse().getContentAsString());
        // Assert.assertEquals(true,re);
    }
}
