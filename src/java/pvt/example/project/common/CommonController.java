package pvt.example.project.common;

import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类&emsp;&emsp;名：CommonController <br/>
 * 描&emsp;&emsp;述：通用控制器
 */
@Controller
public class CommonController {
    /**
     * 验证码的GET请求
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    /**
     * 登陆页请求=>leaf的login
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 首页请求=>leaf的index
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 测试页面请求=>leaf的test
     */
    @GetMapping({"/test", "/test.html"})
    public String test() {return "common/test";}

    @GetMapping({"/home", "/home.html"})
    public String home() {return "common/home";}

    @GetMapping("/monitor/online")
    public String online() {return "monitor/online/online";}

    @GetMapping("/monitor/server")
    public String server() {return "monitor/server/server";}
}
