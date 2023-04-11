package cn.wxxlamp.yanque.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author wxxlamp
 * @date 2023/04/11~22:40
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class YuqueHttpApiTest {

    @Resource
    private YuqueHttpApi yuqueHttpApi;

    @Test
    public void test() {
        yuqueHttpApi.queryDocDetail("developer", "repo");
    }
}
