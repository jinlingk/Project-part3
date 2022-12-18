package edu.nyu.lk.cookzilla.interceptor;

import edu.nyu.lk.cookzilla.bean.response.AuthInterceptorResponse;
import edu.nyu.lk.cookzilla.dao.UserInfoMapper;
import edu.nyu.lk.cookzilla.model.UserInfo;
import edu.nyu.lk.cookzilla.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("token");
        AuthInterceptorResponse authInterceptorResponse = new AuthInterceptorResponse(); authInterceptorResponse.setCode(501);
        PrintWriter out = null;
        if (token != null) {
            String username = "";
            try{
                username = JwtUtil.getUserNameByToken(request);
            }
            catch (Exception e)
            {
                authInterceptorResponse.setMsg("token格式错误");
                JSONObject jsonObject = JSONObject.fromObject(authInterceptorResponse);
                String jsonstr = jsonObject.toString();
                out = response.getWriter();
                out.append(jsonstr);
                return false;
            }
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(username);
            String password = userInfo.getPassword();
            boolean verifyResult = JwtUtil.verify(token, username, password);
            if(verifyResult){
                return true;
            }
        }

        authInterceptorResponse.setCode(502);
        authInterceptorResponse.setMsg("no token");
        JSONObject jsonObject = JSONObject.fromObject(authInterceptorResponse);
        String jsonstr = jsonObject.toString();
        out = response.getWriter();
        out.append(jsonstr);
        return false;
    }
}
