package com.zhengmenbb.book.service;

import java.util.List;

import com.zhengmenbb.book.dao.BookDao;
import com.zhengmenbb.book.exception.ParameterException;
import com.zhengmenbb.book.model.Book;
import com.zhengmenbb.book.model.BookStatus;
import com.zhengmenbb.book.model.Pagination;
import com.zhengmenbb.book.util.StringUtil;
import com.zhengmenbb.common.BeanFactory;

public class BookService {

    private BookDao bookDao = (BookDao)BeanFactory.getInstance().getBean("bookDao");

    public int save(Book book) throws ParameterException {

        ParameterException parameterException = new ParameterException();

        if (StringUtil.isEmpty(book.getName())) {
            parameterException.addErrorField("name", "请填写书名");
        }
        if (StringUtil.isEmpty(book.getAuthor())) {
            parameterException.addErrorField("author", "请填写作者 ");
        }
        if (parameterException.isErrorField()) {
            throw parameterException;
        }

        if (book.getId() > 0) {
            bookDao.update(book);
        } else {
            bookDao.add(book);
        }

        return book.getId();
    }

    public List<Book> query(int userId, BookStatus status,Pagination pagination) {
        return bookDao.query(userId, status, pagination);
    }

    public int getMyBookCount(int userId, BookStatus status) {
        return bookDao.getMyBookCount(userId, status);
    }

    public Book getById(int id) {
        return bookDao.getById(id);
    }

    public void deleteById(int id) {
        bookDao.updateDeleted(id, 1);
    }
}
