package cn.itcast.chatroomdemo.server.service;

/*
*  用户管理接口
* */
public interface UserService {
    /**
     * @Description:
     * @Author: chenb
     * @Email: lcy0869@gmail.com
     * @Date: 2022/6/30 11:58 
     * @param: username
     * @param: password 
     * @return: 登录成功返回 true, 否则返回 false
     **/
    boolean login(String username, String password);
}
