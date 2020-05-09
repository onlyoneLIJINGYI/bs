package com.ljy.bs.dto;
import com.ljy.bs.dto.base.OutputConverter;
import com.ljy.bs.entity.AdminRole;
import com.ljy.bs.entity.User;
import lombok.Data;
import lombok.ToString;
import java.util.List;

/**dto（Data Transfer Object数据传输对象），是经过处理后的PO，可能增加或者减少PO的属性
 * User实体中含有password属性，该属性不需要传递到客户端，使用dto传递出去password的属性，避免在客户端暴露服务端表结构
 * */
@Data
@ToString
public class UserDTO implements OutputConverter<UserDTO, User> {

    private int id;

    private String username;

    private String name;

    private String sex;

    private String phone;

    private String email;

    private boolean enabled;

    private List<AdminRole> roles;

}


