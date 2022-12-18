package edu.nyu.lk.cookzilla.services;

import edu.nyu.lk.cookzilla.dao.UserInfoMapper;
import edu.nyu.lk.cookzilla.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterServices {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public boolean createAccount(UserInfo accountInfo){
        try{
            userInfoMapper.insert(accountInfo);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
