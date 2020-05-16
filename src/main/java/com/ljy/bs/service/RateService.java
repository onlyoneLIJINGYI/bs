package com.ljy.bs.service;

import com.ljy.bs.dao.RateDAO;
import com.ljy.bs.dao.BookDAO;
import com.ljy.bs.entity.Book;
import com.ljy.bs.service.UserService;
import com.ljy.bs.service.BookService;
import com.ljy.bs.entity.Rate;
import com.ljy.bs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RateService {
    @Autowired
    RateDAO rateDAO;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;

    public List<Rate> listAllByRid(int rid) {
        //根据资源id查询所有评价，对评价列表中每一项进行循环查询用户信息
        List<Rate> ratelist=rateDAO.findAllByRid(rid);
        ratelist.forEach(rate -> {
            User user = userService.findById(rate.getUid());
            rate.setUser(user);
        });
        return ratelist;
    }

    //查询全部资源评价信息
    public List<Rate> listAll() {
        //查询所有评价，对评价列表中每一项进行循环查询用户信息和资源信息
        List<Rate> ratelist=rateDAO.findAll();
        ratelist.forEach(rate -> {
            User user = userService.findById(rate.getUid());
            rate.setUser(user);
            Book book=bookService.findById(rate.getRid());
            rate.setBook(book);
        });
        return ratelist;
    }
}
