package com.jw.shopping.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.jw.shopping.dto.User;
import com.jw.shopping.dto.User.Role;

public class RoleTypeHandler extends BaseTypeHandler<Role> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setInt(i, parameter.getValue());
        } else {
            ps.setNull(i, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public Role getResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return Role.valueOf(value);
    }

    @Override
    public Role getResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return Role.valueOf(value);
    }

    @Override
    public Role getResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return Role.valueOf(value);
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, User.Role parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public User.Role getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : User.Role.valueOf(value);
    }

    @Override
    public User.Role getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : User.Role.valueOf(value);
    }

    @Override
    public User.Role getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : User.Role.valueOf(value);
    }
}