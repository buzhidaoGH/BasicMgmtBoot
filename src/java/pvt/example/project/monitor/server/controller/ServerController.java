package pvt.example.project.monitor.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pvt.example.common.basic.Result;
import pvt.example.common.utils.SystemInfoUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：ServerController <br/>
 * 描&emsp;&emsp;述：服务控制器
 */
@Controller
@RequestMapping("/server")
public class ServerController {
    @PostMapping("/server_info")
    @ResponseBody
    public Result<Map<String, Object>> serverInfo() {
        Map<String, Object> infoMap = new HashMap<>();
        List<Map<String, String>> diskCpuInfo = SystemInfoUtil.getDiskCpuInfo();
        List<Map<String, String>> memoryInfo = SystemInfoUtil.getMemoryInfo();
        Map<String, String> serverInfo = SystemInfoUtil.getServerInfo();
        Map<String, String> jvmInfo = SystemInfoUtil.getJVMInfo();
        infoMap.put("diskCpuInfo", diskCpuInfo);
        infoMap.put("memoryInfo", memoryInfo);
        infoMap.put("serverInfo", serverInfo);
        infoMap.put("jvmInfo", jvmInfo);
        return new Result<>(infoMap);
    }
}
