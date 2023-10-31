package pvt.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pvt.example.common.config.MyBannerConfig;

/**
 * 类&emsp;&emsp;名：BootMain <br/>
 * 描&emsp;&emsp;述：使用内置的 服务器 启动类
 */
@SpringBootApplication
@MapperScan("pvt.example.**.dao")
public class BootMain {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootMain.class);
        application.setBanner(new MyBannerConfig());
        application.run(args);
    }
}
