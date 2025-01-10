package com.jw.shopping.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.jw.shopping.dto.Board;
import com.jw.shopping.dto.Board.BoardType;

public class BoardTypeHandler extends BaseTypeHandler<BoardType> {

    @Override
    public void setParameter(PreparedStatement ps, int i, BoardType parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setString(i, parameter.name());
        } else {
            ps.setNull(i, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public BoardType getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return BoardType.valueOf(value);
    }

    @Override
    public BoardType getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return BoardType.valueOf(value);
    }

    @Override
    public BoardType getResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return BoardType.valueOf(value);
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Board.BoardType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public Board.BoardType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : Board.BoardType.valueOf(value);
    }

    @Override
    public Board.BoardType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : Board.BoardType.valueOf(value);
    }

    @Override
    public Board.BoardType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : Board.BoardType.valueOf(value);
    }
}