package cz.org.appointment.entity;


public class ResponseEntity<T> {

    //响应码 默认200为正常
    private int code;

    //响应信息
    private String msg;

    //数据
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
