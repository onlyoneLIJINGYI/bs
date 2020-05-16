package com.ljy.bs.dao;

import com.ljy.bs.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeDAO extends JpaRepository<Like,Integer> {
    List<Like>  findAllByUid(int uid);
}
