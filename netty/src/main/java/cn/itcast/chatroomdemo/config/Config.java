package cn.itcast.chatroomdemo.config;

import cn.itcast.chatroomdemo.protocol.Serializer;
import io.netty.channel.ChannelOption;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.config
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-07-06  22:23
 * @Description: 将一些 配置 单独抽取到 配置文件 中,本类负责从配置文件中读取
 * @Version: 1.0
 */
public class Config {
    static Properties properties;
    static {
        try (InputStream in = Config.class.getResourceAsStream("/application.properties")){
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public static int getServerPort() {
        String value = properties.getProperty("server.port");
        if (value == null) {
            return 8080;
        } else {
            return Integer.parseInt(value);
        }
    }
    public static Serializer.Algorithm getSerializerAlgorithm() {
        String value = properties.getProperty("serializer.algorithm");
        if (value == null) {
            return Serializer.Algorithm.Java;
        } else {
            return Serializer.Algorithm.valueOf(value);
        }
    }
}
