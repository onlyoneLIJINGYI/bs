package com.ljy.bs.result;

//需要安装lombok插件
import lombok.Data;

/**Result类  请求返回的响应码
 *
 */
@Data
public class Result {
    private int code;
    private String message;
    private Object result;

    Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.result = data;
    }
}