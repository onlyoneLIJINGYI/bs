package com.ljy.bs.dao;

import com.ljy.bs.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RateDAO extends JpaRepository<Rate,Integer> {
    //根据资源id查询所有评分
    List<Rate> findAllByRid(int rid);
}
