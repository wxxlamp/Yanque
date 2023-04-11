package cn.wxxlamp.yanque.service;

import cn.wxxlamp.yanque.repo.YuqueHttpApi;
import cn.wxxlamp.yanque.service.model.DocDetailSerializer;
import cn.wxxlamp.yanque.service.model.DocFormatEnum;
import cn.wxxlamp.yanque.service.model.DocNode;
import cn.wxxlamp.yanque.service.model.DocNodeOpe;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author chenkai
 * @version 2023/4/9 16:16
 */
@Service
@RequiredArgsConstructor
public class YuqueService {

    @Value("${yuque.raw.repo.id}")
    private String rawRepoId;

    private final YuqueHttpApi yuqueHttpApi;

    public DocDetailSerializer queryRawDocDetail(DocDetailSerializer docDetail) {
        return yuqueHttpApi.queryDocDetail(rawRepoId, docDetail.getSlug()).getData();
    }

    public DocDetailSerializer createDocFromRaw(DocDetailSerializer rawDocDetail, String targetRepoId) {
        DocDetailSerializer targetDocDetail = new DocDetailSerializer();
        targetDocDetail.setBody(rawDocDetail.getBody_lake());
        targetDocDetail.setFormat(DocFormatEnum.LAKE.getCode());
        targetDocDetail.setSlug(rawDocDetail.getSlug());
        targetDocDetail.setTitle(rawDocDetail.getTitle());
        replaceRawLink2CopyLink(targetDocDetail, targetRepoId);
        return yuqueHttpApi.createDoc(targetRepoId, targetDocDetail).getData();
    }

    public DocDetailSerializer updateDocFromRaw(DocDetailSerializer rawDocDetail, String targetRepoId) {
        DocDetailSerializer targetDocDetail = yuqueHttpApi.queryDocDetail(targetRepoId, rawDocDetail.getSlug()).getData();
        targetDocDetail.setBody(rawDocDetail.getBody_lake());
        targetDocDetail.setFormat(DocFormatEnum.LAKE.getCode());
        targetDocDetail.setTitle(rawDocDetail.getTitle());
        targetDocDetail.set_force_asl(true);
        targetDocDetail.setBody_lake(null);
        targetDocDetail.setBody_html(null);
        replaceRawLink2CopyLink(targetDocDetail, targetRepoId);
        return yuqueHttpApi.updateDoc(targetRepoId, String.valueOf(targetDocDetail.getId()), targetDocDetail).getData();
    }

    public void createNode4TargetDoc(String targetRepoId, DocDetailSerializer rawDocDetail, DocDetailSerializer targetDocDetail) {
        // 1. 获取原知识库目录节点
        List<DocNode> rawDocNodeList = yuqueHttpApi.queryDocNode(rawRepoId).getData();
        // 1.1 获取文档节点
        DocNode rawDoc = rawDocNodeList.stream()
                .filter(e -> StringUtils.equals(e.getType(), "DOC"))
                .filter(e -> Integer.valueOf(e.getDoc_id()).equals(rawDocDetail.getId()))
                .findAny().get();
        // 1.2 获取文档的目录节点
        DocNode rawTitle = rawDocNodeList.stream().filter(e -> StringUtils.equals(rawDoc.getParent_uuid(), e.getUuid())).findAny().get();

        // 2. 获取新知识库的目录
        List<DocNode> targetDocNodeList = yuqueHttpApi.queryDocNode(targetRepoId).getData();
        // 2.1.1 获取文档节点
        Optional<DocNode> targetDocOptional = targetDocNodeList.stream()
                .filter(e -> StringUtils.equals(e.getType(), "DOC"))
                .filter(e -> Integer.valueOf(e.getDoc_id()).equals(targetDocDetail.getId()))
                .findAny();
        // 2.2 如果有该文档，则比较目录是否一致，不一致则更新目录
        if (targetDocOptional.isPresent()) {
            DocNode targetTitle = targetDocNodeList.stream().filter(e -> StringUtils.equals(e.getUuid(), targetDocOptional.get().getParent_uuid())).findAny().get();
            changeNodeTitle(targetRepoId, rawTitle, targetTitle);
        }
        // 2.3 如果没有该文档节点，则新建
        else {
            createDocNode(targetRepoId, targetDocDetail.getId(), rawTitle, targetDocNodeList);
        }

    }

    /**
     * TODO 此时目录中的文章会出现位置不一致情况 && 目录也会出现位置不一样的情况
     */
    private void createDocNode(String targetRepoId, int docId, DocNode rawNode, List<DocNode> targetDocNodeList) {
        DocNode targetNode = createTitleNode(targetRepoId, rawNode, targetDocNodeList);
        DocNodeOpe docNodeOpe = new DocNodeOpe();
        docNodeOpe.setDoc_ids(new int[]{docId});
        docNodeOpe.setAction("appendByDocs");
        docNodeOpe.setTarget_uuid(targetNode.getUuid());
        yuqueHttpApi.opeDocNode(targetRepoId, docNodeOpe).getData().stream().filter(e -> StringUtils.equals(String.valueOf(docId), e.getDoc_id())).findAny().get();
    }

    private DocNode createTitleNode(String targetRepoId, DocNode rawNode, List<DocNode> targetDocNodeList) {
        // 1. 查询是否有新知识库中是否有该节点
        Optional<DocNode> targetTitleNodeOptional = targetDocNodeList.stream().filter(e -> StringUtils.equals(e.getTitle(), rawNode.getTitle())).findAny();
        if (targetTitleNodeOptional.isPresent()) {
            return targetTitleNodeOptional.get();
        }

        // 2. 如果没有，则新建
        DocNodeOpe docNodeOpe = new DocNodeOpe();
        docNodeOpe.setTitle(rawNode.getTitle());
        docNodeOpe.setAction("append");
        docNodeOpe.setType("TITLE");
        return yuqueHttpApi.opeDocNode(targetRepoId, docNodeOpe).getData().stream().filter(e -> StringUtils.equals(rawNode.getTitle(), e.getTitle())).findAny().get();
    }

    private void changeNodeTitle(String targetRepoId, DocNode rawNode, DocNode targetNode) {
        if (StringUtils.equals(targetNode.getTitle(), rawNode.getTitle())) {
            return;
        }
        DocNodeOpe docNodeOpe = new DocNodeOpe();
        docNodeOpe.setAction("edit");
        docNodeOpe.setNode_uuid(targetNode.getUuid());
        docNodeOpe.setTitle(rawNode.getTitle());
        yuqueHttpApi.opeDocNode(targetRepoId, docNodeOpe);
    }

    private void replaceRawLink2CopyLink(DocDetailSerializer targetDocDetail, String targetRepoId) {
        String body = targetDocDetail.getBody();
        targetDocDetail.setBody(body.replaceAll(rawRepoId, targetRepoId));
    }
}
