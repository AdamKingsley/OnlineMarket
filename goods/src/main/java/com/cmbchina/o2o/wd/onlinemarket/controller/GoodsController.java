package com.cmbchina.o2o.wd.onlinemarket.controller;


import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.CartService;
import com.cmbchina.o2o.wd.onlinemarket.service.GoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;


    @GetMapping("category/list")
    public Result catList(@RequestParam(required = false) Long rootId) {
        // 目前目录是两层，虽然支持多层，但是层数越多，查询性能越差
        return goodsService.getCategoryList(rootId == null ? 0L : rootId);
    }

    @GetMapping("category/{id}")
    public Result getCat(@PathVariable Long id) {
        // 目前目录是两层，虽然支持多层，但是层数越多，查询性能越差
        return goodsService.getCategory(id);
    }


    @GetMapping("list")
    public PageResult goodsList(@ModelAttribute GoodsFilterCommand command) {
        return goodsService.getGoodsList(command);
    }

    @GetMapping("detail")
    public Result goodsDetail(@RequestParam("goodsId") Long goodId) {
        return goodsService.getGoodsDetail(goodId);
    }
}
