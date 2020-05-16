package com.ljy.bs.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="likes")
@ToString
//前后端数据交互是json格式，user类对象会被转换为json数据，后端使用jpa做实体类持久化，jpa创造的代理类会添加以下两个属性无需json化，因此使用JsonIgnoreProperties将两个属性忽略
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lid")
    int lid;
    int uid;
    int pid;

}
