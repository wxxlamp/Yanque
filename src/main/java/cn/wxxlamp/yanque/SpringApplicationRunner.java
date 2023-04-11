package cn.wxxlamp.yanque;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author chenkai
 * @version 2023/2/26 22:00
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "cn.cn.wxxlamp.yanque.config")
@RetrofitScan("com.github.lianjiatech.retrofit.spring.boot.test")
public class SpringApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationRunner.class, args);
    }
}
