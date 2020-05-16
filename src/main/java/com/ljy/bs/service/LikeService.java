package com.ljy.bs.service;


import com.ljy.bs.dao.LikeDAO;
import com.ljy.bs.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    LikeDAO likeDAO;

    /**测试推荐功能新增代码
     * */
    public List<Like> findAllByUid(int uid){
        return likeDAO.findAllByUid(uid);
    }
}
