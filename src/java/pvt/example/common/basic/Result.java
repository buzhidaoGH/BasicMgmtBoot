package pvt.example.common.basic;

/**
 * 类&emsp;&emsp;名：Result <br/>
 * 描&emsp;&emsp;述：统一格式返回结果类
 */
public class Result<T> {
    private Integer code;   // 返回码
    private String message; // 返回的简要描述信息
    private T data;         // 多类型数据返回
    private Long count;     // 分页查询的总记录数

    public Result() { }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
        this.count = null;
    }

    public Result(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
        this.count = null;
    }

    public Result(Integer code, String message, T data, Long count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", count=" + count +
                '}';
    }
}
