package cn.wxxlamp.yanque.service.model;

/**
 * @author chenkai
 * @version 2023/4/10 20:59
 */
public enum DocFormatEnum {

    /**
     * HTML格式
     */
    HTML("html"),

    /**
     * HTML格式
     */
    LAKE("lake"),

    /**
     * HTML格式
     */
    MARKDOWN("markdown");

    private final String code;

    DocFormatEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
