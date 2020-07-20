package com.cmbchina.o2o.wd.onlinemarket.entity;


import com.cmbchina.o2o.wd.onlinemarket.entity.base.BaseIdEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_goods_category")
@javax.persistence.Table(name = "t_goods_category")
// @EnableSnakeToCamel
public class GoodsCategory extends BaseIdEntity {

    // 分类名称
    @Column(length = 50)
    private String name;

    @Column(name = "parent_id")
    private Integer parentId;


    @Column(length = 500)
    private String description;

}
