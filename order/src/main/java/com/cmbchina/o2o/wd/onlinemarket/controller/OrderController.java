package com.cmbchina.o2o.wd.onlinemarket.controller;


import com.cmbchina.o2o.wd.onlinemarket.command.order.*;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @GetMapping("/list")
    public PageResult orderList(@ModelAttribute OrderFilterCommand command) {
        return orderService.getOrderList(command, request);
    }

    @GetMapping("/detail/{code}")
    public Result orderDetail(@PathVariable String code) {
        return orderService.getOrderDetail(code, request);
    }

    @PostMapping("/add")
    public Result addOrder(@RequestBody OrderCommand command) {
        return orderService.addOrder(command, request);
    }


    @PutMapping("/update/state")
    public Result updateOrder(@RequestBody OrderAttrUpdateCommand command) {
        // 主要是更新order的状态
        return orderService.updateOrderState(command, request);
    }

    @PutMapping("/update/all/state")
    public Result updateOrder(@RequestBody OrderUpdateAllCommand command) {
        // 主要是更新order的状态
        return orderService.updateAllOrderState(command, request);
    }


    @PutMapping("/update/info")
    public Result updateOrder(@RequestBody OrderUpdateCommand command) {
        // 主要是更新order的状态
        return orderService.updateOrderInfo(command, request);
    }

    // 原则上不允许删除订单
    @Deprecated
    @DeleteMapping("/{id}")
    public Result removeOrder(@PathVariable Long id) {
        return orderService.removeOrder(id, request);
    }
}
