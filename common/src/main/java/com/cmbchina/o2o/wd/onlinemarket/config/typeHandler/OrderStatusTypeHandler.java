package com.cmbchina.o2o.wd.onlinemarket.config.typeHandler;

import com.cmbchina.o2o.wd.onlinemarket.constant.OrderStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(value = OrderStatus.class)
public class OrderStatusTypeHandler implements TypeHandler<OrderStatus> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, OrderStatus baseEnum, JdbcType jdbcType)
            throws SQLException {
        if (baseEnum != null) {
            preparedStatement.setInt(i, baseEnum.getValue());
        } else {
            preparedStatement.setInt(i, 0);
        }
    }

    @Override
    public OrderStatus getResult(ResultSet resultSet, String s) throws SQLException {
        return OrderStatus.getByValue(resultSet.getInt(s));
    }

    @Override
    public OrderStatus getResult(ResultSet resultSet, int i) throws SQLException {
        return OrderStatus.getByValue(resultSet.getInt(i));
    }

    @Override
    public OrderStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        return OrderStatus.getByValue(callableStatement.getInt(i));
    }

}
