package com.ljy.bs.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="papers")
@ToString
//前后端数据交互是json格式，user类对象会被转换为json数据，后端使用jpa做实体类持久化，jpa创造的代理类会添加以下两个属性无需json化，因此使用JsonIgnoreProperties将两个属性忽略
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    private String author;
    private String title;
    private int likecnt;

    /**存储物品相似度，只在推荐时使用
     使用 @transient 注解是在给某个javabean上需要添加个属性，但是这个属性你又不希望给存到数据库中去，仅仅是做个临时变量，用一下。不修改已经存在数据库的数据的数据结构。
     * */
    @Transient
    private double w;
}
