package com.ljy.bs.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单实体类，与表 admin_menu 对应
 *
 */
@Data
@Entity
@Table(name = "admin_menu")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /* 菜单对应的路由中的path*/
    private String path;

    /* Menu name. */
    private String name;

    /* 菜单的中文名称*/
    private String nameZh;

    /* 菜单icon的类名(use element-ui icons).*/
    private String iconCls;

    /*当前菜单路由对应的组件.*/
    private String component;

    /*父节点 id，用于存储导航栏层级关系*/
    private int parentId;

    /* 用来存储菜单中的子节点 */
    @Transient
    private List<AdminMenu> children;
}
