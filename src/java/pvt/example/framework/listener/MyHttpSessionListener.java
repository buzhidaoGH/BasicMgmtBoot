package pvt.example.framework.listener;

import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：MyHttpSessionListener <br/>
 * 描&emsp;&emsp;述：统计在线人数的SessionListener <br/>
 * url:https://blog.csdn.net/qq_38091831/article/details/82912831 <br/>
 * url2: https://blog.csdn.net/zouyang920/article/details/123330560 <br/>
 * url3: https://juejin.cn/s/spring获取所有的session <br/>
 * url4: https://www.cnblogs.com/jing99/p/7826478.html
 */
public class MyHttpSessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    public static Map<String, HttpSession> sessionOnlineMap = new HashMap<>();

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        boolean existFlag = sessionOnlineMap.containsKey(session.getId());
        if (existFlag) {
            sessionOnlineMap.remove(session.getId());
        }
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        HttpSession session = se.getSession();
        if ("userInfo".equals(se.getName())) {
            /* // 只允许账户同时登陆一个
              Set<String> onlineMapKeys = sessionOnlineMap.keySet();
              SysUser nowUserInfo = (SysUser) session.getAttribute("userInfo");
              for (String onlineMapKey : onlineMapKeys) {
                HttpSession httpSession = sessionOnlineMap.get(onlineMapKey);
                SysUser oldUserInfo = (SysUser) httpSession.getAttribute("userInfo");
                if (oldUserInfo.getUserId().equals(nowUserInfo.getUserId())) {
                    httpSession.removeAttribute("userInfo");
                    httpSession.invalidate();
                    break;
                }}
             */
            sessionOnlineMap.put(session.getId(), session);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        HttpSession session = se.getSession();
        boolean existFlag = sessionOnlineMap.containsKey(session.getId());
        if (existFlag) {
            sessionOnlineMap.remove(session.getId());
        }
    }
}