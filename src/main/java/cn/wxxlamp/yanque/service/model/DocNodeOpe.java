package cn.wxxlamp.yanque.service.model;

import lombok.Data;

/**
 * @author chenkai
 * @version 2023/4/9 23:32
 */
@Data
public class DocNodeOpe {

    private String action;

    private String node_uuid;

    private String target_uuid;

    private String title;

    private String type;

    private int[] doc_ids;
}
