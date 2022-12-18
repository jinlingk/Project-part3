package edu.nyu.lk.cookzilla.bean.response;

public class AuthResponse {
    private String token;

    private Long userId;

    private String msg;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
                ", msg='" + msg + '\'' +
                '}';
    }

    public AuthResponse() {
    }

    public AuthResponse(String token, Long userId, String msg) {
        this.token = token;
        this.userId = userId;
        this.msg = msg;
    }
}
