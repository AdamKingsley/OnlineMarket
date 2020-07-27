package com.cmbchina.o2o.wd.onlinemarket.mapper;

import com.cmbchina.o2o.wd.onlinemarket.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface GoodsMapper  extends Mapper<Goods> {
    @Select("select * from t_goods where id = #{id} for update")
    Goods selectById(@Param("id") Long id);

    @Update("update t_goods set saled_num = saled_num + #{count} where id = #{id}")
    int updateSaledNum(@Param("id") Long id,@Param("count") int count);
}
