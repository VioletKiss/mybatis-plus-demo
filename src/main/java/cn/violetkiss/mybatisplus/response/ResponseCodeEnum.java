package cn.violetkiss.mybatisplus.response;

/**
 * 请求返回code 封装
 *
 * @author wrb
 * @date 2020/6/28 10:06
 */
public enum ResponseCodeEnum {

    SUCCESS(0, "请求成功"),
    DB_ERROR(200000, "数据操作失败"),
    ;

    private int code;

    private String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }}
