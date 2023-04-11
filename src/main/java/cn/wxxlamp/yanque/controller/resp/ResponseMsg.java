package cn.wxxlamp.yanque.controller.resp;

/**
 * @author chenkai
 * @version 2023/2/26 22:17
 */
public enum ResponseMsg {

    /**
     * 请求成功
     */
    REQUEST_SUCCESS(200, "success"),

    /**
     * 非法参数
     */
    ILLEGAL_PARAM(400, "param illegal"),

    /**
     * 服务器内部异常
     */
    INTERNAL_ERROR(500, "internal error")
    ;
    private final int code;

    private final String msg;

    ResponseMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
