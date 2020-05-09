package com.ljy.bs.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;


/*user实体类，与数据库进行映射，连接user表
* @Entity 是一个必选的注解，声明这个类对应了一个数据库表。
* @Table(name = "user") 是一个可选的注解。声明了数据库实体对应的表信息
* @Id 注解声明了实体唯一标识对应的属性。
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
@ToString
//前后端数据交互是json格式，user类对象会被转换为json数据，后端使用jpa做实体类持久化，jpa创造的代理类会添加以下两个属性无需json化，因此使用JsonIgnoreProperties将两个属性忽略
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    private String username;
    private String password;
    private String salt;
    private String name;
    private String sex;
    @Email(message = "请输入正确的邮箱")
    private String email;
    private String phone;
    private boolean enabled;

    /**存储当前用户对应的角色
    使用 @transient 注解是在给某个javabean上需要添加个属性，但是这个属性你又不希望给存到数据库中去，仅仅是做个临时变量，用一下。不修改已经存在数据库的数据的数据结构。
    * */
    @Transient
    private List<AdminRole> roles;


}

