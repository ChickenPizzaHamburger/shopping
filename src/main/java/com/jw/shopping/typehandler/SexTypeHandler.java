package com.jw.shopping.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.jw.shopping.dto.User;
import com.jw.shopping.dto.User.Sex;

public class SexTypeHandler extends BaseTypeHandler<Sex> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Sex parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setInt(i, parameter.getValue());
        } else {
            ps.setNull(i, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public Sex getResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return Sex.fromInt(value);
    }

    @Override
    public Sex getResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return Sex.fromInt(value);
    }

    @Override
    public Sex getResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return Sex.fromInt(value);
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, User.Sex parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public User.Sex getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : User.Sex.valueOf(value);
    }

    @Override
    public User.Sex getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : User.Sex.valueOf(value);
    }

    @Override
    public User.Sex getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : User.Sex.valueOf(value);
    }
}