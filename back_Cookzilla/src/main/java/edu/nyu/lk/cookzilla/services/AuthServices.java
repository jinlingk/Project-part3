package edu.nyu.lk.cookzilla.services;

import edu.nyu.lk.cookzilla.bean.request.AuthRequest;
import edu.nyu.lk.cookzilla.dao.UserInfoMapper;
import edu.nyu.lk.cookzilla.model.UserInfo;
import edu.nyu.lk.cookzilla.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthServices {

    @Autowired()
    private UserInfoMapper userInfoMapper;

    /**
     * token expiration time, default 1h
     */
    public static final long EXPIRE_TIME = 300 * 60 * 10000;

    public String authByUsernameAndPassword(AuthRequest authRequest){
        if(checkUsernameAndPasswd(authRequest.getUsername(), authRequest.getPassword())){
            String token = JwtUtil.sign(authRequest.getUsername(), authRequest.getPassword(), EXPIRE_TIME);
            return token;
        }else{
            return null;
        }
    }

    private boolean checkUsernameAndPasswd(String username, String password)
    {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(username);
        if (userInfo == null)
            return false;
        if (userInfo.getPassword().equals(password))
            return true;
        else
            return false;
    }
    public boolean isIn(String username){
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(username);
        if(userInfo == null){
            return false;
        }
        else{
            return true;
        }
    }
}
