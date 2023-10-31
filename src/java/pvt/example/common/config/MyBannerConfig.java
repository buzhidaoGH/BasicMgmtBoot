package pvt.example.common.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import pvt.example.common.utils.IPUtil;
import pvt.example.common.utils.PadStringUtil;

import java.io.PrintStream;

/**
 * 类&emsp;&emsp;名：MyBannerConfig <br/>
 * 描&emsp;&emsp;述：自定义Banner
 */
public class MyBannerConfig implements Banner {
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        String localIp = IPUtil.getLocalIp();
        String port = environment.getProperty("server.port");
        String property = environment.getProperty("server.servlet.context-path");
        String contextPath = property == null ? "" : property;
        String appName = environment.getProperty("myapp.name");
        String appDesc = environment.getProperty("myapp.desc");
        String appVersion = environment.getProperty("myapp.version");

        out.println(PadStringUtil.padCenter("SpringVersion:" + SpringVersion.getVersion(), 60, '='));
        out.println(">>@项目名称：" + appName);
        out.println(">>@项目版本：" + appVersion);
        out.println(">>@项目描述：" + appDesc);
        out.println(PadStringUtil.padCenter("Local: http://localhost:" + port + contextPath + "/", 60, ' '));
        out.println(PadStringUtil.padCenter("External: http://" + localIp + ":" + port + contextPath + "/", 60, ' '));
        out.println(PadStringUtil.padCenter("SpringBootVersion:" + SpringBootVersion.getVersion(), 60, '='));
    }
}
