package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.order.OrderGoodsCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.entity.*;
import com.cmbchina.o2o.wd.onlinemarket.mapper.*;
import com.cmbchina.o2o.wd.onlinemarket.service.OrderService;
import com.cmbchina.o2o.wd.onlinemarket.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsAttrMapper goodsAttrMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public PageResult getOrderList(OrderFilterCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);

        //TODO implement
        return PageResult.success();
    }

    @Override
    public Result getOrderDetail(Long id, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);

        //TODO implement
        return Result.success();
    }

    @Override
    public Result addOrder(OrderCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        command.setUserId(userId);
        // 默认是可插入的，先去减库存
        // boolean flag = true;
        List<OrderGoodsCommand> doneList = Lists.newArrayList();
        for (OrderGoodsCommand goodsCommand : command.getOrderGoodsList()) {
            // 代理调用内部的public事务方法
            Result result = ((OrderService) AopContext.currentProxy()).lockRepository(goodsCommand);
            if (!result.isSuccess()) {
                break;
            }
            doneList.add(goodsCommand);
        }
        // 开始复盘
        if (doneList.size()!=command.getOrderGoodsList().size()){
            // 这一批订单处理不了，开始rollback一个一个给我加回去
            doneList.forEach(goodsCommand->rollbackRepository(goodsCommand));
            return Result.fail().setStatus(ResultStatus.REPOSITORY_NOT_ENOUGH);
        }
        // 能处理，开始疯狂插入操作！(库存都减了还怕!)
        List<Long> orderIds = Lists.newArrayList();
        Map<Long, List<OrderDetail>> maps = processOrderCommand(command);
        for (Long merchantId : maps.keySet()) {
            Order order = new Order();
            BeanUtils.copyProperties(command, order);
            order.setMerchantId(merchantId);
            // 设置code
            order.setCode(maps.get(merchantId).get(0).getCode());
            orderMapper.insert(order);
            orderIds.add(order.getId());
            maps.get(merchantId).forEach(orderDetail -> orderDetailMapper.insert(orderDetail));
        }
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setData(orderIds).setMessage("提交订单成功，跳转付款页面！");
    }

    @Override
    @Transactional
    public Result lockRepository(OrderGoodsCommand command) {
        int count = command.getCount();
        int storage = 0;
        if (command.getAttrId()!=null){
            GoodsAttr attr = goodsAttrMapper.selectById(command.getAttrId());
            storage = attr.getStoreNum()-attr.getSaledNum();
        }else {
            Goods goods = goodsMapper.selectById(command.getGoodsId());
            storage = goods.getStoreNum()-goods.getSaledNum();
        }
        // 库存不足
        if (storage < count){
            return Result.fail();
        }
        // 充足开始更新数据
        if (command.getAttrId()!=null){
            goodsAttrMapper.updateSaledNum(command.getAttrId(),count);
        }
        goodsMapper.updateSaledNum(command.getGoodsId(),count);
        return Result.success();
    }

    private Result rollbackRepository(OrderGoodsCommand command){
        int count = command.getCount();
        if (command.getAttrId()!=null){
            goodsAttrMapper.updateSaledNum(command.getAttrId(),-count);
        }
        goodsMapper.updateSaledNum(command.getGoodsId(),-count);
        return Result.success();
    }

    private Map<Long, List<OrderDetail>> processOrderCommand(OrderCommand command) {
        Map<Long, List<OrderDetail>> maps = Maps.newHashMap();
        if (command.getOrderGoodsList() != null) {
            command.getOrderGoodsList().forEach(detail -> {
                OrderDetail orderDetail = new OrderDetail();
                BeanUtils.copyProperties(detail, orderDetail);
                Long merchantId = orderDetail.getMerchantId();
                if (maps.get(merchantId) == null) {
                    maps.put(merchantId, Lists.newArrayList());
                }
                maps.get(merchantId).add(orderDetail);
            });
            maps.keySet().forEach(key -> {
                String code = StringUtil.genereteOrderCode();
                maps.get(key).forEach(obj -> obj.setCode(code));
            });
        }
        return maps;
    }

    @Override
    public Result updateOrder(OrderCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);

        //TODO implement
        return Result.success();
    }

    @Override
    public Result removeOrder(Long id, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);

        //TODO implement
        return Result.success();
    }

    private Result checkAuthority(HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        if (null == userId) {
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("未检测到登录信息！");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("当前用户不可用！");
        }
        return Result.success();
    }

}
