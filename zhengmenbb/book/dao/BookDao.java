package com.zhengmenbb.book.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.zhengmenbb.book.model.Book;
import com.zhengmenbb.book.model.BookStatus;
import com.zhengmenbb.book.model.Pagination;
import com.zhengmenbb.common.BeanFactory;
import com.zhengmenbb.common.JDBCAbstractCallBack;
import com.zhengmenbb.common.JDBCTemplate;

public class BookDao {

    @SuppressWarnings("unchecked")
    JDBCTemplate<Book> jdbcTemplate = (JDBCTemplate<Book>)BeanFactory.getInstance().getBean("jdbcTemplate");
    public void add(final Book book) {

        String sql = "INSERT INTO book(name, picture, owner_id, owner_name, current_owner_id, current_owner_name, author, description, created_time, updated_time) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW()) ";

        int id = jdbcTemplate.insert(sql, new JDBCAbstractCallBack<Book>() {
            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, book.getName());
                stmt.setString(2, book.getPicture());
                stmt.setInt(3, book.getOwnerId());
                stmt.setString(4, book.getOwnerName());
                stmt.setInt(5, book.getCurrentOwnerId());
                stmt.setString(6, book.getCurrentOwnerName());
                stmt.setString(7, book.getAuthor());
                stmt.setString(8, book.getDesc());
            }

        });
        book.setId(id);

    }

    public void update(final Book book) {

        String sql = "UPDATE book SET name = ?, author = ?, description = ?, updated_time = NOW() WHERE id = " + book.getId();
        jdbcTemplate.update(sql, new JDBCAbstractCallBack<Book>() {

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, book.getName());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getDesc());
            }

        });
    }

    private String getWhereSQL(int userId, String status) {
        String sql = "";

        if (status.equals("all")) {
            sql = " owner_id = " + userId + " AND deleted = 0 ";
        } else if (status.equals("out")) {
            sql = " owner_id = " + userId + " AND owner_id != current_owner_id AND deleted = 0 ";
        } else if (status.equals("in")) {
            sql = " owner_id = " + userId + " AND owner_id = current_owner_id AND deleted = 0 ";
        }  else if (status.equals("borrow")) {
            sql = "current_owner_id = " + userId + " AND owner_id != current_owner_id AND deleted = 0 ";
        }

        return sql;
    }

    public List<Book> query(int userId, BookStatus status, Pagination pagination) {

        pagination.setTotalCount(this.getMyBookCount(userId, status));
        if (pagination.getCurrentPage() > pagination.getPageCount()) {
            pagination.setCurrentPage(pagination.getPageCount());
        }
        String sql = "SELECT * FROM book  WHERE " + this.getWhereSQL(userId, status.getValue()) + " ORDER BY updated_time DESC LIMIT " + pagination.getOffset() +"," + pagination.getPageSize();

        List<Book> books = jdbcTemplate.query(sql, new JDBCAbstractCallBack<Book>() {

            @Override
            public Book rsToObject(ResultSet rs) throws SQLException {
                return rsToBook(rs);
            }

        });
        return books;
    }

    private Book rsToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setAuthor(rs.getString("author"));
        book.setDesc(rs.getString("description"));
        book.setName(rs.getString("name"));
        book.setPicture(rs.getString("picture"));
        book.setOwnerId(rs.getInt("owner_id"));
        book.setCurrentOwnerId(rs.getInt("current_owner_id"));
        book.setCreatedTime(rs.getDate("created_time"));
        book.setUpdatedTime(rs.getDate("updated_time"));
        book.setOwnerName(rs.getString("owner_name"));
        book.setCurrentOwnerName(rs.getString("current_owner_name"));
        return book;
    }

    public int getMyBookCount(int userId, BookStatus status) {
        String sql = "SELECT count(*) AS mybookCount FROM book WHERE " + this.getWhereSQL(userId, status.getValue());
        return jdbcTemplate.getCount(sql);

    }

    public Book getById(int id) {

        String sql = "SELECT * FROM book AS b WHERE id = " + id;
        return jdbcTemplate.queryOne(sql, new JDBCAbstractCallBack<Book>() {
            @Override
            public Book rsToObject(ResultSet rs) throws SQLException {
                return rsToBook(rs);
            }
        });

    }

    public void deleteById(int id) {

    }

    public void updateDeleted(int id, int deleted) {
        String sql = "UPDATE book SET deleted = "+ deleted +", updated_time = NOW() WHERE id = " + id;
        jdbcTemplate.update(sql);

    }
}
