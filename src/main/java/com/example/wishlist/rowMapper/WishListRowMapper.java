package com.example.wishlist.rowMapper;

import com.example.wishlist.model.WishList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishListRowMapper implements RowMapper<WishList> {
    @Override
    public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WishList(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}
