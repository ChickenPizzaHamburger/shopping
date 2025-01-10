package com.jw.shopping.typehandler;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {

    @Override
    public void setParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setString(i, parameter.toString());
        } else {
            ps.setNull(i, jdbcType.TYPE_CODE);  // NULL Ã³¸®
        }
    }

    @Override
    public LocalDate getResult(ResultSet rs, String columnName) throws SQLException {
        String date = rs.getString(columnName);
        return (date != null) ? LocalDate.parse(date) : null;
    }

    @Override
    public LocalDate getResult(ResultSet rs, int columnIndex) throws SQLException {
        String date = rs.getString(columnIndex);
        return (date != null) ? LocalDate.parse(date) : null;
    }

    @Override
    public LocalDate getResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        String date = cs.getString(columnIndex);
        return (date != null) ? LocalDate.parse(date) : null;
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Date date = rs.getDate(columnName);
        return date != null ? date.toLocalDate() : null;
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Date date = rs.getDate(columnIndex);
        return date != null ? date.toLocalDate() : null;
    }

    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Date date = cs.getDate(columnIndex);
        return date != null ? date.toLocalDate() : null;
    }
}