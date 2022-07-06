package cn.itcast.chatroomdemo.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.server.service
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-30  11:59
 * @Description:
 * @Version: 1.0
 */
public class UserServiceMemoryImpl implements UserService{

    private Map<String, String> allUserMap = new ConcurrentHashMap<>();

    {
        allUserMap.put("zhangsan", "123");
        allUserMap.put("lisi", "123");
        allUserMap.put("wangwu", "123");
        allUserMap.put("zhaoliu", "123");
        allUserMap.put("qianqi", "123");
    }

    @Override
    public boolean login(String username, String password) {
        String pass = allUserMap.get(username);
        if (pass == null) {
            return false;
        }
        return pass.equals(password);
    }
}
