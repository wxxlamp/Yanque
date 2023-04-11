package cn.wxxlamp.yanque.service.model;

import lombok.Data;

/**
 * @author chenkai
 * @version 2023/4/9 16:24
 */
@Data
public class DocDetailSerializer {

    private Integer id;

    private String slug;

    private String title;

    /**
     * markdown，html，lake
     */
    private String format;

    private String body;

    private String body_lake;

    private String body_html;

    /**
     * for update
     */
    private Boolean _force_asl;
}
