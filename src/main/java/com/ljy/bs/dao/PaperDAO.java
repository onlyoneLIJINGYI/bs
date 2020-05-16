package com.ljy.bs.dao;

import com.ljy.bs.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperDAO extends JpaRepository<Paper,Integer> {
    Paper findById(int id);
}
