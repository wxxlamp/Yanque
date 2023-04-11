package cn.wxxlamp.yanque.controller;

import cn.wxxlamp.yanque.controller.req.YuqueWebhookReq;
import cn.wxxlamp.yanque.controller.resp.ApiResponse;
import cn.wxxlamp.yanque.service.YuqueService;
import cn.wxxlamp.yanque.service.model.DocDetailSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenkai
 * @version 2023/4/9 16:15
 */
@RestController
@RequestMapping("/yuque")
@RequiredArgsConstructor
public class YuqueWebhookController {

    private final static Logger LOGGER = LoggerFactory.getLogger(YuqueWebhookController.class);

    private final static String PUBLISH = "publish";

    private final static String UPDATE = "update";

    private final static String DELETE = "delete";

    private final YuqueService yuqueService;

    @Value("{yuque.target.repo.list}")
    private List<String> targetRepoList;

    @ResponseBody
    @PostMapping("/webhook")
    public ApiResponse<Void> createOrUpdateArticle(@RequestBody YuqueWebhookReq req) {

        try {
            // 1. 查询文章
            DocDetailSerializer rawDocDetail = yuqueService.queryRawDocDetail(req.getDocDetailSerializer());

            // 2. 操作文章
            for (String targetRepoId : targetRepoList) {
                creatOrUpdate(rawDocDetail, targetRepoId, req.getAction_type());
            }
        } catch (Exception ex) {
            LOGGER.error("sync error", ex);
        }


        return ApiResponse.succeed();
    }

    private void creatOrUpdate(DocDetailSerializer rawDocDetail, String targetRepoId, String action) {
        DocDetailSerializer targetDoc;
        // 创建文章
        if (StringUtils.equals(PUBLISH, action)) {
            targetDoc = yuqueService.createDocFromRaw(rawDocDetail, targetRepoId);
        } else if (StringUtils.equals(UPDATE, action)) {
            targetDoc = yuqueService.updateDocFromRaw(rawDocDetail, targetRepoId);
        } else {
            return;
        }
        // 操作目录
        yuqueService.createNode4TargetDoc(targetRepoId, rawDocDetail, targetDoc);
    }
}
