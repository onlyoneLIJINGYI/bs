package com.ljy.bs.dao;
import com.ljy.bs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//DAO(data access object),用来操作数据库的对象，使用jpa对数据库进程操作，不需要写sql语句，findByUsername方法即根据参数username查询对应的行返回给User类
public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findById(int id);
    User getByUsernameAndPassword(String username,String password);
}
