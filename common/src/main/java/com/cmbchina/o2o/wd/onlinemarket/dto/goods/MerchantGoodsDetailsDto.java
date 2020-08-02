package com.cmbchina.o2o.wd.onlinemarket.dto.goods;

import lombok.Data;

import java.util.List;

@Data
public class MerchantGoodsDetailsDto extends MerchantGoodsDto {
    private List<MerchantGoodAttrDto> attrs;
}
