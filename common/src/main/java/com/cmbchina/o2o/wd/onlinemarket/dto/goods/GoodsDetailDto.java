package com.cmbchina.o2o.wd.onlinemarket.dto.goods;

import com.cmbchina.o2o.wd.onlinemarket.entity.GoodsAttr;
import lombok.Data;

import java.util.List;

/**
 * 查看商品详情时的传输对象
 */
@Data
public class GoodsDetailDto extends GoodsDto {
    private List<GoodsAttrDto> attrs;
}
