package com.ljy.bs.entity;

import com.ljy.bs.entity.User;
import com.ljy.bs.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
/**
 * 评价实体类，用户对资源的评价
* */

@Data
@Entity
@Table(name = "rate")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    //资源id
    private int rid;
    //角色id
    private int uid;
    //评分
    private int rate;
    //评价
    private String comment;
    /**存储当前评论对应的用户
     使用 @transient 注解是在给某个javabean上需要添加个属性，但是这个属性你又不希望给存到数据库中去，仅仅是做个临时变量，用一下。不修改已经存在数据库的数据的数据结构。
     * */
    @Transient
    private User user;
    @Transient
    private Book book;
}
