package cn.wxxlamp.yanque.repo;

import cn.wxxlamp.yanque.repo.model.YuqueApiResponse;
import cn.wxxlamp.yanque.service.model.DocAbilities;
import cn.wxxlamp.yanque.service.model.DocDetailSerializer;
import cn.wxxlamp.yanque.service.model.DocNode;
import cn.wxxlamp.yanque.service.model.DocNodeOpe;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import retrofit2.http.*;

import java.util.List;

/**
 * @author chenkai
 * @version 2023/4/9 21:42
 */
@RetrofitClient(baseUrl = "https://www.yuque.com")
public interface YuqueHttpApi {

    /**
     * 查询文档内容
     * @param repoId 知识库ID
     * @param slug 文档slug
     * @return 文档内容
     */
    @GET("/api/v2/repos/{namespace}/{repoId}/docs/{slug}")
    @Headers("X-Auth-Token:{token}}")
    YuqueApiResponse<DocAbilities, DocDetailSerializer> queryDocDetail(@Path("repoId") String repoId, @Path("slug") String slug);

    /**
     * 创建文档
     * @param repoId 知识库ID
     * @param docDetail 文档详情
     * @return 文档内容
     */
    @POST("/api/v2/repos/{namespace}/{repoId}/docs")
    @Headers("X-Auth-Token:{token}")
    YuqueApiResponse<Void, DocDetailSerializer> createDoc(@Path("repoId") String repoId, @Body DocDetailSerializer docDetail);

    /**
     * 更新文档
     * @param repoId
     * @param docId
     * @param docDetail
     * @return
     */
    @PUT("/api/v2/repos/{namespace}/{repoId}/docs/{docId}")
    @Headers("X-Auth-Token:{token}")
    YuqueApiResponse<Void, DocDetailSerializer> updateDoc(@Path("repoId") String repoId, @Path("docId") String docId, @Body DocDetailSerializer docDetail);

    /**
     * 查询目录
     * @param repoId
     * @return
     */
    @GET("/api/v2/repos/{namespace}/{repoId}/toc")
    @Headers("X-Auth-Token:{token}")
    YuqueApiResponse<Void, List<DocNode>> queryDocNode(@Path("repoId") String repoId);

    /**
     * 操作文档
     * @param repoId
     * @param docNodeOpe
     * @return
     */
    @PUT("/api/v2/repos/{namespace}/{repoId}/toc")
    @Headers("X-Auth-Token:{token}")
    YuqueApiResponse<Void, List<DocNode>> opeDocNode(@Path("repoId") String repoId, @Body DocNodeOpe docNodeOpe);
}
