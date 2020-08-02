package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsAttrCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsCommand;
import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.GoodsDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.MerchantGoodAttrDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.MerchantGoodsDetailsDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.MerchantGoodsDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.merchant.MerchantDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.*;
import com.cmbchina.o2o.wd.onlinemarket.mapper.*;
import com.cmbchina.o2o.wd.onlinemarket.service.GoodsService;
import com.cmbchina.o2o.wd.onlinemarket.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsAttrMapper goodsAttrMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MerchantMapper merchantMapper;

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;


    @Override
    public Result addGoods(GoodsCommand command, HttpServletRequest request) {
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return state;
        }
        if (null == command.getCategoryId()) {
            // 如果是空的字段，后端辅助获取商户id
            User user = (User) state.getData();
            Example example = new Example(Merchant.class);
            example.createCriteria().andEqualTo(Strings.USER_ID,user.getId());
            Merchant merchant = merchantMapper.selectOneByExample(example);
            if (merchant==null){
                return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("获取商户信息失败！");
            }
            command.setMerchantId(merchant.getId());
        }
        // 正式开始插入
        Goods goods = new Goods();
        BeanUtils.copyProperties(command,goods);
        // 填写imgs字段，逗号隔开+
        goods.setImgs(String.join(Strings.COMMA,command.getImgs()));
        goodsMapper.insert(goods);
        // 插入成功后，遍历attr插入
        command.getAttrs().forEach(attr->{
            GoodsAttr goodsAttr = new GoodsAttr();
            BeanUtils.copyProperties(attr,goodsAttr);
            goodsAttr.setGoodsId(goods.getId());
            goodsAttrMapper.insert(goodsAttr);
        });
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }

    @Override
    @Transactional
    // 将更新商品信息设置为事务性操作
    // 最好查找sql加for update锁（暂时停止买卖）
    public Result updateGoods(GoodsCommand command, HttpServletRequest request) {
        String message = "更改商品信息成功！";
        Goods goods = goodsMapper.selectByPrimaryKey(command.getId());
        // 开始遍历更新attr对象
        for (GoodsAttrCommand attrCommand : command.getAttrs()) {
            if (null == attrCommand.getId()){
                // 若是新增，直接插入
                GoodsAttr goodsAttr = new GoodsAttr();
                BeanUtils.copyProperties(attrCommand,goodsAttr);
                goodsAttr.setGoodsId(goods.getId());
                goodsAttrMapper.insert(goodsAttr);
            }else{
                GoodsAttr goodsAttr = goodsAttrMapper.selectByPrimaryKey(attrCommand.getId());
                if(attrCommand.isCodeChanged() || attrCommand.isPriceChanged()){
                    //先删后增加一条记录（删除相当于做了一个快照）
                    goodsAttr.setIsDeleted(true);
                    CopyUtil.updateObject(goodsAttr);
                    goodsAttrMapper.updateByPrimaryKey(goodsAttr);
                    // 删完新增
                    //GoodsAttr attr = new GoodsAttr();
                    goodsAttr.setIsDeleted(false);
                    goodsAttr.setId(null);
                    // 此时goodsAttr拿到新的id
                    goodsAttrMapper.insert(goodsAttr);
                    // System.out.println(id);
                    attrCommand.setId(goodsAttr.getId());
                    //goodsAttr = attr;
                }
                if(attrCommand.isStoreChanged()){
                    //若库存改变需要考虑是否小于已卖出的数量
                    if (goodsAttr.getSaledNum()>attrCommand.getStoreNum()){
                        message = "部分套餐库存数量设置少于售出数量，更新失败！";
                        //更改商品的总数值（恢复）
                        goods.setStoreNum(goods.getStoreNum()+(goodsAttr.getStoreNum()-attrCommand.getStoreNum()));
                        attrCommand.setStoreNum(goodsAttr.getStoreNum());
                    }
                }
                // 统一更新goodsAttr
                BeanUtils.copyProperties(attrCommand,goodsAttr);
                CopyUtil.updateObject(goodsAttr);
                goodsAttrMapper.updateByPrimaryKey(goodsAttr);
            }
        }
        // 最后再更新goods
        if (command.getAttrs().size()==0 && command.getStoreNum()<goods.getSaledNum()){
            //不存在套餐情况，要单独考虑goods的库存
            command.setStoreNum(goods.getStoreNum());
            message = "部分套餐库存数量设置少于售出数量，更新失败！";
        }
        BeanUtils.copyProperties(command,goods);
        goods.setImgs(String.join(Strings.COMMA,command.getImgs()));
        CopyUtil.updateObject(goods);
        goodsMapper.updateByPrimaryKey(goods);
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setMessage(message);
    }

    @Override
    public Result removeGoods(Long id, HttpServletRequest request) {

        Goods goods = goodsMapper.selectByPrimaryKey(id);
        // 已删除的情况
        if (null == goods || goods.getIsDeleted() == true) {
            return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
        }
        goods.setIsDeleted(true);
        CopyUtil.updateObject(goods);
        goodsMapper.updateByPrimaryKey(goods);
        // 开始删除对应的套餐记录
        Example example = new Example(GoodsAttr.class);
        example.createCriteria().andEqualTo(Strings.GOODS_ID, goods.getId());
        List<GoodsAttr> attrList = goodsAttrMapper.selectByExample(example);
        attrList.forEach(attr -> {
            attr.setIsDeleted(true);
            CopyUtil.updateObject(attr);
            goodsAttrMapper.updateByPrimaryKey(attr);
        });
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }

    @Override
    public Result removeAttr(Long id, HttpServletRequest request) {
        GoodsAttr attr = goodsAttrMapper.selectByPrimaryKey(id);
        if (null == attr || attr.getIsDeleted() == true) {
            return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
        }
        attr.setIsDeleted(true);
        CopyUtil.updateObject(attr);
        goodsAttrMapper.updateByPrimaryKey(attr);
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS);
    }

    @Override
    public PageResult getGoodsList(GoodsFilterCommand command, HttpServletRequest request) {
        Result state = checkAuthority(request);
        if (!state.isSuccess()) {
            return PageResult.fail();
        }
        if (command.getMerchantId() == null){
            Example example = new Example(Merchant.class);
            example.createCriteria().andEqualTo(Strings.USER_ID,((User)state.getData()).getId());
            Merchant merchant = merchantMapper.selectOneByExample(example);
            if (null == merchant){
                return PageResult.fail();
            }
            command.setMerchantId(merchant.getId());
        }
        PageHelper.startPage(command.getPageNum(), command.getPageSize());
        processCategory(command);
        List<MerchantGoodsDto> list = goodsMapper.selectGoodsList(command);
        PageInfo page = new PageInfo(list);
        PageResult result = PageResult.success();
        result.setCurrentPage(command.getPageNum()).setSize(command.getPageSize()).setTotalNum(page.getTotal()).setTotalPage(page.getPages());
        result.setData(list);
        return result;
    }

    private void processCategory(GoodsFilterCommand command) {
        if (command.getCategoryId() == null || command.getMerchantId() == 0L) {
            return;
        }
        Long id = command.getCategoryId();
        command.getCategoryList().add(id);
        Example example = new Example(GoodsCategory.class);
        example.createCriteria().andEqualTo(Strings.PARENT_ID, id);
        List<GoodsCategory> categories = goodsCategoryMapper.selectByExample(example);
        categories.forEach(category -> command.getCategoryList().add(category.getId()));
    }

    @Override
    public Result getGoodsDetail(Long goodId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodId);
        Example example = new Example(GoodsAttr.class);
        example.createCriteria().andEqualTo(Strings.GOODS_ID,goodId)
        .andEqualTo(Strings.IS_DELETED,false);
        List<GoodsAttr> attrList = goodsAttrMapper.selectByExample(example);
        MerchantGoodsDetailsDto dto = new MerchantGoodsDetailsDto();
        BeanUtils.copyProperties(goods,dto);
        List<MerchantGoodAttrDto> attrDtos = Lists.newArrayList();
        attrList.forEach(attr->{
            MerchantGoodAttrDto attrDto = new MerchantGoodAttrDto();
            BeanUtils.copyProperties(attr,attrDto);
            attrDtos.add(attrDto);
        });
        dto.setAttrs(attrDtos);
        return Result.success().setStatus(ResultStatus.OPERATION_SUCCESS).setData(dto);
    }


    private Result checkAuthority(HttpServletRequest request) {
        String userId = request.getHeader(Strings.USER_ID_KEY);
        if (null == userId) {
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("未检测到登录信息！");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getType() != UserType.MERCHANT) {
            return Result.fail().setStatus(ResultStatus.AUTHRIZATION_ERROR).setMessage("仅支持商戶进行该操作！");
        }
        return Result.success().setData(user);
    }

}
