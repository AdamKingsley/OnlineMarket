package com.cmbchina.o2o.wd.onlinemarket.service.impl;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.constant.ResultStatus;
import com.cmbchina.o2o.wd.onlinemarket.constant.Strings;
import com.cmbchina.o2o.wd.onlinemarket.dto.PageResult;
import com.cmbchina.o2o.wd.onlinemarket.dto.Result;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.CategoryDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.GoodsAttrDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.GoodsDetailDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.GoodsDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.Goods;
import com.cmbchina.o2o.wd.onlinemarket.entity.GoodsAttr;
import com.cmbchina.o2o.wd.onlinemarket.entity.GoodsCategory;
import com.cmbchina.o2o.wd.onlinemarket.mapper.GoodsAttrMapper;
import com.cmbchina.o2o.wd.onlinemarket.mapper.GoodsCategoryMapper;
import com.cmbchina.o2o.wd.onlinemarket.mapper.GoodsMapper;
import com.cmbchina.o2o.wd.onlinemarket.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsAttrMapper goodsAttrMapper;

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public Result getCategoryList(long rootId) {
        Example example = new Example(GoodsCategory.class);
        example.createCriteria().andEqualTo(Strings.PARENT_ID, rootId);
        List<GoodsCategory> categories = goodsCategoryMapper.selectByExample(example);
        List<CategoryDto> dtos = Lists.newArrayList();
        categories.forEach(category -> {
            CategoryDto dto = new CategoryDto();
            BeanUtils.copyProperties(category, dto);
            dtos.add(dto);
        });
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES).setData(dtos);
    }

    @Override
    public PageResult getGoodsList(GoodsFilterCommand command) {
        PageHelper.startPage(command.getPageNum(), command.getPageSize());
        List<GoodsDto> list = goodsMapper.selectGoodsList(command);
        processCategory(command);
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
        GoodsDto dto = goodsMapper.selectGoodsById(goodId);
        Example example = new Example(GoodsAttr.class);
        example.createCriteria().andEqualTo(Strings.IS_DELETED,false)
        .andEqualTo(Strings.GOODS_ID,goodId);
        List<GoodsAttr> goodsAttrs = goodsAttrMapper.selectByExample(example);
        List<GoodsAttrDto> attrDtos = Lists.newArrayList();
        goodsAttrs.forEach(goodsAttr -> {
            GoodsAttrDto attrDto = new GoodsAttrDto();
            BeanUtils.copyProperties(goodsAttr,attrDto);
            attrDtos.add(attrDto);
        });
        GoodsDetailDto detailDto = new GoodsDetailDto();
        BeanUtils.copyProperties(dto,detailDto);
        detailDto.setAttrs(attrDtos);
        return Result.success().setStatus(ResultStatus.OPERATION_SUCEES).setData(detailDto);
    }

}
