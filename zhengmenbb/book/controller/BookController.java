package com.zhengmenbb.book.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.zhengmenbb.book.Constants;
import com.zhengmenbb.book.exception.ParameterException;
import com.zhengmenbb.book.model.Book;
import com.zhengmenbb.book.model.BookStatus;
import com.zhengmenbb.book.model.Pagination;
import com.zhengmenbb.book.model.User;
import com.zhengmenbb.book.service.BookService;
import com.zhengmenbb.book.util.StringUtil;
import com.zhengmenbb.common.BeanFactory;
import com.zhengmenbb.common.ModelAndView;

public class BookController {

    private static final String EDIT_BOOK_PAGE = "/WEB-INF/jsp/book/edit.jsp";

    private BookService bookService = (BookService)BeanFactory.getInstance().getBean("bookService");

    private List<String> mockPictures = new ArrayList<String>();

    public BookController() {
        mockPictures.add("/static/book/Book1_50x60.png");
        mockPictures.add("/static/book/Book2_50x60.png");
        mockPictures.add("/static/book/Book3_50x60.png");
        mockPictures.add("/static/book/Book4_50x60.png");
        mockPictures.add("/static/book/Book5_50x60.png");
    }

    private static int getRandom() {
        int max = 5;
        int min = 1;
        Random random = new Random();

        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public ModelAndView save(Map<String, String> request, Map<String, Object> session) {
        String name = request.get("name");
        String author = request.get("author");
        String desc = request.get("desc");

        String idStr = request.get("id");
        int id = Integer.parseInt(idStr);


        User user = (User)session.get(Constants.USER);

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setDesc(desc);
        book.setOwnerId(user.getId());
        book.setCurrentOwnerId(user.getId());
        book.setOwnerName(user.getUserName());
        book.setCurrentOwnerName(user.getUserName());

        book.setPicture(mockPictures.get(getRandom() - 1));

        ModelAndView modelAndView = new ModelAndView();

        try {

            bookService.save(book);
            modelAndView.addSessionAttribute("SUCESS_FLASH_MESSAGE", "保存图书成功");

            modelAndView.setView("sucess");
        } catch (ParameterException parameterException) {
            Map<String, String> errorFields = parameterException.getErrorFields();
            modelAndView.addObject(Constants.ERROR_FIELDS, errorFields);
            modelAndView.addObject("book", book);
            modelAndView.setView("error");
        }

        return modelAndView;

    }

    public ModelAndView delete(Map<String, String> request, Map<String, Object> session) {
        String idStr = request.get("id");

        String status = request.get("status");
        if (StringUtil.isEmpty(status)) {
            status = "all";
        }

        String currentPage = request.get("currentPage");
        if (StringUtil.isEmpty(currentPage)) {
            currentPage = "1";
        }

        int id = Integer.parseInt(idStr);


        User user = (User)session.get(Constants.USER);

        Book book = bookService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("return_page");
        modelAndView.addObject("status", status);
        modelAndView.addObject("currentPage", currentPage);

        if (book.getOwnerId() != user.getId()) {
            modelAndView.addSessionAttribute(Constants.ERROR_FLASH_MESSAGE, "当前没有权限删除此书籍");
            return modelAndView;
        }

        if (book.getOwnerId() != book.getCurrentOwnerId()) {
            modelAndView.addSessionAttribute(Constants.ERROR_FLASH_MESSAGE, "当前书籍已借出, 不能删除");
            return modelAndView;
        }

        bookService.deleteById(id);

        modelAndView.addSessionAttribute(Constants.SUCESS_FLASH_MESSAGE, "删除书籍成功");
        return modelAndView;
    }

    public ModelAndView mybook(Map<String, String> request, Map<String, Object> session) {

        User user = (User)session.get(Constants.USER);

        String status = request.get("status");
        if (StringUtil.isEmpty(status)) {
            status = "all";
        }

        String currentPageStr = request.get("currentPage");
        if (StringUtil.isEmpty(currentPageStr)) {
            currentPageStr = "1";
        }
        int currentPage = Integer.parseInt(currentPageStr);
        if (currentPage < 1) {
            currentPage = 1;
        }
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(currentPage);

        BookStatus statusEnum = null;
        try {
            statusEnum = BookStatus.valueOf(status);
        } catch (Exception ex) {
            statusEnum = BookStatus.valueOf("all");
        }

        ModelAndView modelAndView = new ModelAndView();

        List<Book> books = bookService.query(user.getId(), statusEnum , pagination);

        modelAndView.addObject("status", status);

        modelAndView.addObject("books", books);
        modelAndView.addObject("pagination", pagination);

        for(BookStatus enumStatus :BookStatus.values()){
            int mybookCount = bookService.getMyBookCount(user.getId(), enumStatus);
            modelAndView.addObject(enumStatus.getValue() + "Count", mybookCount);
        }


        modelAndView.setView("sucess");
        return modelAndView;
    }

    public ModelAndView edit(Map<String, String> request, Map<String, Object> session) {
        String idStr = request.get("id");
        Book book = null;

        User user = (User)session.get(Constants.USER);

        ModelAndView modelAndView = new ModelAndView();

        if (StringUtil.isEmpty(idStr)) {
            book = new Book();
        } else {
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception ex) {
                modelAndView.addSessionAttribute(Constants.ERROR_FLASH_MESSAGE, "请检查参数");
                modelAndView.setRedirect(true);
                modelAndView.setView("mybook.action");
                return modelAndView;
            }

            book = bookService.getById(id);
            if (book.getOwnerId() != user.getId()) {
                modelAndView.addSessionAttribute(Constants.ERROR_FLASH_MESSAGE, "当前没有权限修改此书籍");
                modelAndView.setRedirect(true);
                modelAndView.setView("mybook.action");
                return modelAndView;
            }
        }

        modelAndView.addObject("book", book);

        modelAndView.setView("/WEB-INF/jsp/book/edit.jsp");
        return modelAndView;
    }

}
