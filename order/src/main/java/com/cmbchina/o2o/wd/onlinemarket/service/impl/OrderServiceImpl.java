package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.account.AddressCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.order.*;
import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.dto.order.OrderDetailDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.order.OrderDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.*;
import com.cmbchina.o2o.wd.onlinemarket.mapper.*;
import com.cmbchina.o2o.wd.onlinemarket.service.OrderService;
import com.cmbchina.o2o.wd.onlinemarket.util.CopyUtil;
import com.cmbchina.o2o.wd.onlinemarket.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Resource
    private AddressMapper addressMapper;

    @Override
    public PageResult getOrderList(OrderFilterCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return PageResult.fail();
        }
        if (command.getMerchantId() == null) {
            // 如果不是商户查询订单，需要加上userId来查询订单
            command.setUserId(userId);
        }
        PageHelper.startPage(command.getPageNum(), command.getPageSize());
        if (!StringUtil.isEmpty(command.getLabel()) || !StringUtil.isEmpty(command.getName())) {
            List<String> codes = orderDetailMapper.selectCodeList(command);
            command.setCodeList(codes);
        }
        List<OrderDto> list = orderMapper.selectOrderList(command);
        PageInfo page = new PageInfo(list);
        PageResult result = PageResult.success();
        result.setCurrentPage(command.getPageNum()).setSize(command.getPageSize()).setTotalNum(page.getTotal()).setTotalPage(page.getPages());
        result.setData(list);
        // PageResult pageResult = PageResult.success();
        // pageResult.setMessage("获取订单列表成功！").setData(list);
        return result;
    }

    @Override
    public Result getOrderDetail(String code, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return PageResult.fail();
        }
        List<OrderDetailDto> list = orderDetailMapper.selectOrderDetailList(code);
        return Result.success().setData(list).setMessage("获取订单详情成功！");
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
        if (doneList.size() != command.getOrderGoodsList().size()) {
            // 这一批订单处理不了，开始rollback一个一个给我加回去
            doneList.forEach(goodsCommand -> rollbackRepository(goodsCommand));
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
            maps.get(merchantId).forEach(orderDetail -> {
                orderDetail.setAddressId(order.getAddressId());
                orderDetail.setOrderStatus(order.getOrderStatus());
                orderDetailMapper.insert(orderDetail);
            });
        }
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setData(orderIds).setMessage("提交订单成功，跳转付款页面！");
    }

    @Override
    @Transactional
    public Result lockRepository(OrderGoodsCommand command) {
        int count = command.getCount();
        int storage = 0;
        if (command.getAttrId() != null) {
            GoodsAttr attr = goodsAttrMapper.selectById(command.getAttrId());
            storage = attr.getStoreNum() - attr.getSaledNum();
        } else {
            Goods goods = goodsMapper.selectById(command.getGoodsId());
            storage = goods.getStoreNum() - goods.getSaledNum();
        }
        // 库存不足
        if (storage < count) {
            return Result.fail();
        }
        // 充足开始更新数据
        if (command.getAttrId() != null) {
            goodsAttrMapper.updateSaledNum(command.getAttrId(), count);
        }
        goodsMapper.updateSaledNum(command.getGoodsId(), count);
        return Result.success();
    }

    @Override
    public Result updateOrderState(OrderAttrUpdateCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return PageResult.fail();
        }
        Example example = new Example(OrderDetail.class);
        Example.Criteria  criteria = example.createCriteria().andEqualTo(Strings.CODE,command.getCode());
        if(Objects.nonNull(command.getGoodsId())){
            criteria.andEqualTo(Strings.GOODS_ID,command.getGoodsId());
        }
        if(Objects.nonNull(command.getAttrId())){
            criteria.andEqualTo(Strings.ATTR_ID,command.getAttrId());
        }
        OrderDetail orderDetail = orderDetailMapper.selectOneByExample(example);
        // BeanUtils.copyProperties(command,orderDetail);
        // 只更新订单的状态
        orderDetail.setOrderStatus(command.getOrderStatus());
        CopyUtil.updateObject(orderDetail);
        orderDetailMapper.updateByPrimaryKey(orderDetail);
        return Result.success().setMessage("更新订单状态成功！");
    }

    @Override
    public Result updateOrderInfo(OrderUpdateCommand command, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return PageResult.fail();
        }
        Example example = new Example(Order.class);
        example.createCriteria().andEqualTo(Strings.CODE,command.getCode());
        Order order = orderMapper.selectOneByExample(example);
        order.setOrderStatus(command.getOrderStatus());
        CopyUtil.updateObject(order);
        orderMapper.updateByPrimaryKey(order);
        command.getDetails().forEach(detail ->{
            //更改状态会立即调用update/order命令，因此只需要更新地址
            if(detail.isAddressChanged()){
            Example example1 = new Example(OrderDetail.class);
            Example.Criteria  criteria = example1.createCriteria().andEqualTo(Strings.CODE,detail.getCode());
            if(Objects.nonNull(detail.getGoodsId())){
                criteria.andEqualTo(Strings.GOODS_ID,detail.getGoodsId());
            }
            if(Objects.nonNull(detail.getAttrId())){
                criteria.andEqualTo(Strings.ATTR_ID,detail.getAttrId());
            }
             // OrderDetail orderDetail = orderDetailMapper.selectByPrimaryKey(detail.getDetailId());
             OrderDetail orderDetail = orderDetailMapper.selectOneByExample(example1);
             Address address = new Address();
             BeanUtils.copyProperties(detail.getAddress(),address);
             addressMapper.insert(address);
             orderDetail.setAddress(address.getAddress());
             orderDetail.setAddressId(address.getId());
             CopyUtil.updateObject(orderDetail);
             orderDetailMapper.updateByPrimaryKey(orderDetail);
            }
        });
        return Result.success().setMessage("更新订单信息成功！");

    }

    private Result rollbackRepository(OrderGoodsCommand command) {
        int count = command.getCount();
        if (command.getAttrId() != null) {
            goodsAttrMapper.updateSaledNum(command.getAttrId(), -count);
        }
        goodsMapper.updateSaledNum(command.getGoodsId(), -count);
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
    public Result updateAllOrderState(OrderUpdateAllCommand command, HttpServletRequest request){
        command.getIds().parallelStream().forEach(id->{
            Order order = orderMapper.selectByPrimaryKey(id);
            order.setOrderStatus(command.getOrderStatus());
            CopyUtil.updateObject(order);
            orderMapper.updateByPrimaryKey(order);
            Example example = new Example(OrderDetail.class);
            example.createCriteria().andEqualTo(Strings.CODE,order.getCode());
            List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(example);
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setOrderStatus(command.getOrderStatus());
                CopyUtil.updateObject(orderDetail);
                orderDetailMapper.updateByPrimaryKey(orderDetail);
            }
        });
        return Result.success().setMessage("更新订单状态成功！");
    }


    @Override
    public Result removeOrder(Long id, HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        // implement
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
        return Result.success().setData(user);
    }

}
