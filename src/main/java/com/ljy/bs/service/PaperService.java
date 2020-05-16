package com.ljy.bs.service;

import com.ljy.bs.dao.PaperDAO;
import com.ljy.bs.entity.Paper;
import com.ljy.bs.utils.ItemSimiliarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperService {
    @Autowired
    PaperDAO paperDAO;

    /*//推荐资源的方法
    public List<Paper> recommendPapersByUid(int uid){
        List<Paper> recomLists=ItemSimiliarity.recommend(uid);
        return recomLists;
    }*/


    public List<Paper> findAll(){
        System.out.println(paperDAO.findAll());
        return paperDAO.findAll();
    }

    public Paper findByPid(int pid) {
        return paperDAO.findById(pid);
    }
}
