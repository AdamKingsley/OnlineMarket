package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.CartCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.goods.ShoppingCartFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.CartDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.ShoppingCartDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.ShoppingCart;
import com.cmbchina.o2o.wd.onlinemarket.entity.User;
import com.cmbchina.o2o.wd.onlinemarket.mapper.ShoppingCartMapper;
import com.cmbchina.o2o.wd.onlinemarket.mapper.UserMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.CartService;
import com.cmbchina.o2o.wd.onlinemarket.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Result addCart(CartCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return state;
        }
        command.setUserId(userId);
        Example example = new Example(ShoppingCart.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo(Strings.IS_DELETED, false)
                .andEqualTo(Strings.GOODS_ID, command.getGoodsId()).andEqualTo(Strings.USER_ID, userId);
        if (null != command.getAttrId()) {
            criteria.andEqualTo(Strings.ATTR_ID, command.getAttrId());
        }

        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectByExample(example);
        if (shoppingCartList.size() > 0) {
            // 含有则更新
            ShoppingCart cart = shoppingCartList.get(0);
            cart.setCount(cart.getCount() + command.getCount());
            command.setCount(cart.getCount());
            BeanUtils.copyProperties(command, cart, CopyUtil.getNullPropertyNames(command));
            CopyUtil.updateObject(cart);
            shoppingCartMapper.updateByPrimaryKey(cart);
        } else {
            // 没有则新增
            ShoppingCart cart = new ShoppingCart();
            BeanUtils.copyProperties(command, cart);
            shoppingCartMapper.insert(cart);
        }
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }


    @Override
    public Result updateCart(CartCommand command, HttpServletRequest request) {
        // 更新cart的数据，只支持更新count
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return state;
        }
        ShoppingCart cart = null;
        if (null != command.getId()) {
            cart = shoppingCartMapper.selectByPrimaryKey(command.getId());
        } else {
            Example example = new Example(ShoppingCart.class);
            Example.Criteria criteria = example.createCriteria().andEqualTo(Strings.IS_DELETED, false)
                    .andEqualTo(Strings.GOODS_ID, command.getGoodsId()).andEqualTo(Strings.USER_ID, userId);
            if (null != command.getAttrId()) {
                criteria.andEqualTo(Strings.ATTR_ID, command.getAttrId());
            }
            List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectByExample(example);
            if (shoppingCartList.size() > 0) {
                cart = shoppingCartList.get(0);
            }
        }
        if (cart != null) {
            cart.setCount(command.getCount());
            CopyUtil.updateObject(cart);
            shoppingCartMapper.updateByPrimaryKey(cart);
        }
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }

    @Override
    public Result removeCart(Long id, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return state;
        }
        // 刪除对应选项
        ShoppingCart entity = shoppingCartMapper.selectByPrimaryKey(id);
        if (null != entity && entity.getUserId().equals(userId)) {
            // 只能删除自己购物车里面的产品
            entity.setIsDeleted(true);
            shoppingCartMapper.updateByPrimaryKey(entity);
        }
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }

    @Override
    public PageResult getCartList(ShoppingCartFilterCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return PageResult.fail();
        }
        PageHelper.startPage(command.getPageNum(), command.getPageSize());
        command.setUserId(userId);
        List<CartDto> list = shoppingCartMapper.selectCartList(command);
        List<ShoppingCartDto> carts = processCartList(list);
        //processCategory(command);
        PageInfo page = new PageInfo(carts);
        PageResult result = PageResult.success();
        result.setCurrentPage(command.getPageNum()).setSize(command.getPageSize()).setTotalNum(page.getTotal()).setTotalPage(page.getPages());
        result.setData(carts);
        return result;
    }

    private List<ShoppingCartDto> processCartList(List<CartDto> list) {
        List<ShoppingCartDto> carts = Lists.newArrayList();
        list.forEach(cartDto -> {
            ShoppingCartDto cart = new ShoppingCartDto();
            BeanUtils.copyProperties(cartDto, cart);
            cart.setTitle(cartDto.getGoodsName());
            // 计算价格
            double price = 0.0;
            double disAccount = 0.0;
            // 如果存在attr产品
            // 折后减
            if (cartDto.getAttrPrice() != null && cartDto.getAttrPrice() != 0.0) {
                // 原价计算
                price = cartDto.getAttrPrice() * cartDto.getCount();
                disAccount = price - (price * (1 - cartDto.getAttrDiscountRate() / 100.0) - cartDto.getAttrDiscountAmount() * cartDto.getCount());
            } else {
                price = cartDto.getGoodsPrice() * cartDto.getCount();
                disAccount = price - (price * (1 - cartDto.getGoodsDiscountRate() / 100.0) - cartDto.getGoodsDiscountAmount() * cartDto.getCount());
            }
            cart.setPrice(Math.round(price * 100) / 100.0);
            cart.setDiscountAmount(Math.round(disAccount * 100) / 100.0);
            carts.add(cart);
        });
        return carts;
    }


    private Result checkAuthority(HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        if (null == userId) {
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("未检测到登录信息！");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getType() != UserType.CUSTOMER) {
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("仅支持消费者进行添加购物车操作！");
        }
        return Result.success();
    }


}
