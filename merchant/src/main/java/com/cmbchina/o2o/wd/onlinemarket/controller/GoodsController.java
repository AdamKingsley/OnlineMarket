package com.cmbchina.o2o.wd.onlinemarket.controller;


import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.GoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @GetMapping("list")
    public PageResult goodsList(@ModelAttribute GoodsFilterCommand command) {
        return goodsService.getGoodsList(command,request);
    }

    @GetMapping("detail/{id}")
    public Result goodsDetail(@PathVariable("id") Long id) {
        return goodsService.getGoodsDetail(id);
    }


    @PostMapping("/add")
    public Result addGoods(@RequestBody GoodsCommand command) {
        return goodsService.addGoods(command, request);
    }


    @PutMapping("/update")
    public Result updateGoods(@RequestBody GoodsCommand command) {
        return goodsService.updateGoods(command, request);
    }


    @DeleteMapping("/remove/{id}")
    public Result removeGoods(@PathVariable Long id) {
        return goodsService.removeGoods(id, request);
    }

    @DeleteMapping("/remove/attr/{id}")
    public Result removeGoodsAttr(@PathVariable Long id) {
        return goodsService.removeAttr(id, request);
    }

}
