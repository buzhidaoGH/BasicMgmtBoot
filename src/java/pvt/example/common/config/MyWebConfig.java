package pvt.example.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pvt.example.common.utils.FileUtil;
import pvt.example.framework.listener.MyHttpSessionListener;

import javax.annotation.Resource;
import java.io.File;

/**
 * 类&emsp;&emsp;名：MyWebConfig <br/>
 * 描&emsp;&emsp;述：Web配置类
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Resource
    @Qualifier(value = "loginInterceptor")
    private HandlerInterceptor loginHandlerInterceptor;
    @Value("${myapp.upload}")
    private String uploadStr;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginHandlerInterceptor);
        interceptorRegistration.addPathPatterns("/**"); // 拦截监听过滤所有
        interceptorRegistration.excludePathPatterns(
                "/sys_user/login", "/login", "/test**",
                "/captcha", "/upload/**", "/favicon.ico",
                "/pages/**", "/assets/**"); // 排除静态资源
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //匹配到resourceHandler,将URL映射至location,也就是本地文件夹
        String localUpload = FileUtil.getLocalDirectory(uploadStr);
        File uploadFile = new File(localUpload);
        if (!uploadFile.exists()) {boolean mkdirs = uploadFile.mkdirs();}
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + localUpload);
    }

    @Bean
    public ServletListenerRegistrationBean<MyHttpSessionListener> listenerRegister() {
        ServletListenerRegistrationBean<MyHttpSessionListener> srb = new ServletListenerRegistrationBean<>();
        srb.setListener(new MyHttpSessionListener());
        return srb;
    }
}
