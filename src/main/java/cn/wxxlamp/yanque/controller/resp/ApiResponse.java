package cn.wxxlamp.yanque.controller.resp;

import lombok.Data;

/**
 * @author chenkai
 * @version 2023/2/26 22:05
 */
@Data
public class ApiResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> ApiResponse<T> succeed(T data) {
        ApiResponse<T> response = succeed();
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> succeed() {
        ApiResponse<T> response = new ApiResponse<>();
        build(response, ResponseMsg.REQUEST_SUCCESS);
        return response;
    }

    public static <T> ApiResponse<T> failed(ResponseMsg responseMsg) {
        ApiResponse<T> response = new ApiResponse<>();
        build(response, responseMsg);
        return response;
    }

    private static <T> void build(ApiResponse<T> response, ResponseMsg responseMsg) {
        response.setCode(responseMsg.getCode());
        response.setMsg(responseMsg.getMsg());
    }
}
