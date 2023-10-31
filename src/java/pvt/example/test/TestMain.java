package pvt.example.test;

import pvt.example.common.utils.SystemInfoUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：TestMain <br/>
 * 描&emsp;&emsp;述：测试Main
 */
public class TestMain {
    public static void main(String[] args) {
        Map<String, String> jvmInfo = SystemInfoUtil.getJVMInfo();
        System.out.println("jvmInfo = " + jvmInfo);
    }
}
