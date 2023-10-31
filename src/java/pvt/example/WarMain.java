package pvt.example;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 类&emsp;&emsp;名：WarMain <br/>
 * 描&emsp;&emsp;述：使用外置的 服务器 启动(打war包时需要使用此类)
 */
public class WarMain extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WarMain.class);
    }
}