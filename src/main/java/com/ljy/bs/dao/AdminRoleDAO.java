package com.ljy.bs.dao;

import com.ljy.bs.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface AdminRoleDAO extends JpaRepository<AdminRole, Integer> {
    AdminRole findById(int id);
}