package cn.violetkiss.mybatisplus.response;

import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * 相应包装
 * data可根据业务传 {} 或者加上 @JsonInclude 注解
 *
 * @author wrb
 * @date 2019/11/25 15:11
 */
@Data
public class ServerResponse<T> {

    private T data;
    private String msg;
    private int code;

    /**
     * 使用枚举自带的msg
     *
     * @param responseCodeEnum
     */
    public ServerResponse(ResponseCodeEnum responseCodeEnum) {
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getMsg();
    }

    public ServerResponse(ResponseCodeEnum responseCodeEnum, String msg) {
        this.code = responseCodeEnum.getCode();
        this.msg = msg;
    }

    public static ResponseEntity success() {
        ServerResponse object = new ServerResponse(ResponseCodeEnum.SUCCESS);
        return ResponseEntity.status(200).body(object);
    }

    public static <T> ResponseEntity success(T data) {
        ServerResponse object = new ServerResponse(ResponseCodeEnum.SUCCESS);
        object.setData(data);
        return ResponseEntity.status(200).body(object);
    }

    public static ResponseEntity error(ResponseCodeEnum responseCodeEnum) {
        ServerResponse object = new ServerResponse(responseCodeEnum);
        return ResponseEntity.status(200).body(object);
    }

    public static ResponseEntity error(ResponseCodeEnum responseCodeEnum, String msg) {
        ServerResponse object = new ServerResponse(responseCodeEnum, msg);
        return ResponseEntity.status(200).body(object);
    }

}
