package pvt.example.common.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import pvt.example.common.utils.FileUtil;

/**
 * 类&emsp;&emsp;名：LoggerStartupListenerConfig <br/>
 * 描&emsp;&emsp;述：创建动态日志路径
 */
public class LoggerStartupListenerConfig extends ContextAwareBase implements LoggerContextListener, LifeCycle {
    /** 存储日志路径标识 */
    private static final String LOG_PATH_KEY = "LOG_PATH";

    @Override
    public boolean isResetResistant() {
        return false;
    }

    @Override
    public void onStart(LoggerContext loggerContext) {

    }

    @Override
    public void onReset(LoggerContext loggerContext) {

    }

    @Override
    public void onStop(LoggerContext loggerContext) {

    }

    @Override
    public void onLevelChange(Logger logger, Level level) {

    }

    @Override
    public void start() {
        String logDir = FileUtil.getLocalDirectory("mgmt-log");
        System.setProperty(LOG_PATH_KEY, logDir);
        Context context = getContext();
        context.putProperty(LOG_PATH_KEY, logDir);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }
}
