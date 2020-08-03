package com.cmbchina.o2o.wd.onlinemarket.mapper;

import com.cmbchina.o2o.wd.onlinemarket.command.goods.GoodsFilterCommand;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.GoodsDto;
import com.cmbchina.o2o.wd.onlinemarket.dto.goods.MerchantGoodsDto;
import com.cmbchina.o2o.wd.onlinemarket.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GoodsMapper extends Mapper<Goods> {
    List<MerchantGoodsDto> selectGoodsList(GoodsFilterCommand command);

    @Select("select * from t_goods where id = #{id} for update")
    Goods selectById(@Param("id") Long id);

    @Update("update t_goods set saled_num = saled_num + #{count} where id = #{id}")
    int updateSaledNum(@Param("id") Long id,@Param("count") int count);
}
