package pvt.example.common.utils;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类&emsp;&emsp;名：SystemInfoUtil <br/>
 * 描&emsp;&emsp;述：系统信息工具
 */
public class SystemInfoUtil {
    private static final Runtime runtime = Runtime.getRuntime();
    private static final OperatingSystemMXBean oSMB
            = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private static final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    /**
     * 获取硬盘和CPU基本信息
     * @return 硬盘和CPU基本信息
     */
    public static List<Map<String, String>> getDiskCpuInfo() {
        ArrayList<Map<String, String>> cpuDiskInfoList = new ArrayList<>();
        Map<String, String> processorMap = new HashMap<>();
        String processors = String.valueOf(runtime.availableProcessors());
        processorMap.put("property", "核心数");
        processorMap.put("value", processors);
        Map<String, String> cpuArcMap = new HashMap<>();
        cpuArcMap.put("property", "CPU架构");
        cpuArcMap.put("value", System.getProperty("sun.cpu.isalist"));
        File[] disks = File.listRoots();
        long diskTotalSpace = 0;
        long diskUsableSpace = 0;
        for (File file : disks) {
            diskTotalSpace += file.getTotalSpace();
            diskUsableSpace += file.getUsableSpace();
        }
        Map<String, String> diskTotalMap = new HashMap<>();
        diskTotalMap.put("property", "硬盘存在容量");
        diskTotalMap.put("value", transformGB(diskTotalSpace));
        Map<String, String> diskUsableMap = new HashMap<>();
        diskUsableMap.put("property", "硬盘未用容量");
        diskUsableMap.put("value", transformGB(diskUsableSpace));
        cpuDiskInfoList.add(processorMap);
        cpuDiskInfoList.add(cpuArcMap);
        cpuDiskInfoList.add(diskTotalMap);
        cpuDiskInfoList.add(diskUsableMap);
        return cpuDiskInfoList;
    }

    /**
     * 获取内存Memory基本信息
     * @return 内存Memory基本信息
     */
    public static List<Map<String, String>> getMemoryInfo() {
        long jvmTotal = runtime.totalMemory(); //JVM总申请内存
        long jvmFree = runtime.freeMemory(); // JVM总申请内存的剩余
        long jvmUse = jvmTotal - jvmFree; // JVM总申请内存的使用
        long systemTotal = oSMB.getTotalPhysicalMemorySize(); // 系统总内存
        long systemFree = oSMB.getFreePhysicalMemorySize(); // 系统总内存的剩余
        long systemUse = systemTotal - systemFree; // 系统总内存的使用
        ArrayList<Map<String, String>> memoryInfoList = new ArrayList<>();
        Map<String, String> totalMemoryMap = new HashMap<>();
        totalMemoryMap.put("property", "总内存");
        totalMemoryMap.put("memory", transformGB(systemTotal));
        totalMemoryMap.put("jvm", transformMB(jvmTotal));
        Map<String, String> useMemoryMap = new HashMap<>();
        useMemoryMap.put("property", "已用内存");
        useMemoryMap.put("memory", transformGB(systemUse));
        useMemoryMap.put("jvm", transformMB(jvmUse));
        Map<String, String> freeMemoryMap = new HashMap<>();
        freeMemoryMap.put("property", "剩余内存");
        freeMemoryMap.put("memory", transformGB(systemFree));
        freeMemoryMap.put("jvm", transformMB(jvmFree));
        Map<String, String> rateMemoryMap = new HashMap<>();
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        // 设置保留几位小数，这里设置的是保留两位小数
        percentInstance.setMinimumFractionDigits(1);
        rateMemoryMap.put("property", "使用率");
        rateMemoryMap.put("memory", percentInstance.format((float) systemUse / systemTotal));
        rateMemoryMap.put("jvm", percentInstance.format((float) jvmUse / jvmTotal));
        memoryInfoList.add(totalMemoryMap);
        memoryInfoList.add(useMemoryMap);
        memoryInfoList.add(freeMemoryMap);
        memoryInfoList.add(rateMemoryMap);
        return memoryInfoList;
    }

    /**
     * 获取服务器信息
     * @return 返回服务器信息Map
     */
    public static Map<String, String> getServerInfo() {
        Map<String, String> serverInfoMap = new HashMap<>();
        serverInfoMap.put("serverName", IPUtil.getLocalName());
        serverInfoMap.put("systemName", System.getProperty("os.name"));
        serverInfoMap.put("serverIp", IPUtil.getLocalIp());
        serverInfoMap.put("systemArch", System.getProperty("os.arch"));
        return serverInfoMap;
    }

    /**
     * Java虚拟机信息
     * @return 返回JVM虚拟机信息
     */
    public static Map<String, String> getJVMInfo() {
        Map<String, String> JVMInfoMap = new HashMap<>();
        JVMInfoMap.put("jvmName", System.getProperty("java.vm.name"));
        JVMInfoMap.put("jvmVersion", System.getProperty("java.vm.specification.version")
                + " " + System.getProperty("java.vm.version"));
        JVMInfoMap.put("jvmStartDate", formatDate(runtimeMXBean.getStartTime()));
        JVMInfoMap.put("jvmStartTime", formatDuring(runtimeMXBean.getUptime()));
        JVMInfoMap.put("jvmJrePath", System.getProperty("java.home"));
        JVMInfoMap.put("jvmProjectPath", FileUtil.getLocalDirectory(""));
        JVMInfoMap.put("jvmArgs", String.join(",", runtimeMXBean.getInputArguments()));
        return JVMInfoMap;
    }

    /**
     * 处理为GB或者MB
     * @param size 数字
     * @param type GB | MB
     * @return 带有GB/MB的字符串
     */
    private static String transform(Long size, String type) {
        DecimalFormat df = new DecimalFormat("#.0");
        if ("GB".equals(type)) {
            return df.format((float) size / 1024 / 1024 / 1024) + "GB";
        } else {
            return df.format((float) size / 1024 / 1024) + "MB";
        }
    }

    private static String transformGB(Long size) {
        return transform(size, "GB");
    }

    private static String transformMB(Long size) {
        return transform(size, "MB");
    }

    /**
     * 转换运行的时间
     * @param mss 毫秒
     * @return 毫秒转运行x天x时x分x秒
     */
    private static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + "天 " + hours + "时 " + minutes + "分 " + seconds + "秒";
    }

    /**
     * 格式化毫秒时间
     * @param mss 毫秒
     * @return 格式化毫秒
     */
    private static String formatDate(long mss) {
        Date date = new Date();
        date.setTime(mss);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
