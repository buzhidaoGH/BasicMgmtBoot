package pvt.example.framework.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类&emsp;&emsp;名：LoginInterceptor <br/>
 * 描&emsp;&emsp;述：登陆状态拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取Session,判断是否为登陆状态
        Object userInfo = request.getSession().getAttribute("userInfo");
        if (userInfo == null) {
            LOG.debug("未登录请求：" + request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        LOG.debug("放行请求：" + request.getRequestURI());
        return true;
    }
}
