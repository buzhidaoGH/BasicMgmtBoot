package pvt.example.common.basic;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 功能描述: 捕获全局Exception类型的异常，并进行友好错误提示
     * @param exception 全局异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> error(Exception exception) {
        // exception.printStackTrace(); // 打印异常详情
        System.out.println(exception.toString());// 简单异常
        return new Result<>(-1, exception.getMessage());
    }
}
