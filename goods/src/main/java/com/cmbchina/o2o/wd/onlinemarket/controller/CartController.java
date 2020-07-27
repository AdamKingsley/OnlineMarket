package com.cmbchina.o2o.wd.onlinemarket.controller;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.CartCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.goods.ShoppingCartFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @GetMapping("/list")
    public PageResult cartList(@ModelAttribute ShoppingCartFilterCommand command) {
        return cartService.getCartList(command, request);
    }

    @PostMapping("/add")
    public Result addCart(@RequestBody CartCommand command) {
        return cartService.addCart(command, request);
    }

    @PutMapping("/update")
    public Result updateCart(@RequestBody CartCommand command) {
        return cartService.updateCart(command, request);
    }

    @DeleteMapping("/{id}")
    public Result removeCart(@PathVariable Long id) {
        return cartService.removeCart(id, request);
    }
}
