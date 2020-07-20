package com.cmbchina.o2o.wd.onlinemarket.config.typeHandler;

import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(value = UserType.class)
public class UserTypeHandler implements TypeHandler<UserType> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, UserType baseEnum, JdbcType jdbcType)
            throws SQLException {
        if (baseEnum != null) {
            preparedStatement.setInt(i, baseEnum.getValue());
        } else {
            preparedStatement.setInt(i, 0);
        }
    }

    @Override
    public UserType getResult(ResultSet resultSet, String s) throws SQLException {
        return UserType.getByValue(resultSet.getInt(s));
    }

    @Override
    public UserType getResult(ResultSet resultSet, int i) throws SQLException {
        return UserType.getByValue(resultSet.getInt(i));
    }

    @Override
    public UserType getResult(CallableStatement callableStatement, int i) throws SQLException {
        return UserType.getByValue(callableStatement.getInt(i));
    }
}
