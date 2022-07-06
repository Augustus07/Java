package cn.itcast.chatroomdemo.protocol;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

/*
 * @Description:
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @Date: 2022/7/6 21:59
 * 用于扩展序列化、反序列化算法
 */
public interface Serializer {

    // 序列化方法
    <T> byte[] serialize(T object);

    // 反序列化方法
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    // 给用户提供一些其他的实现
    enum Algorithm implements Serializer {
        // 枚举一 jdk 自带的序列化和反序列化
        Java {
            @Override
            public <T> byte[] serialize(T object) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败", e);
                }
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败", e);
                }
            }
        },

        // 枚举二 json 序列化和反序列化
        Json {
            @Override
            public <T> byte[] serialize(T object) {
                String json = new Gson().toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                return new Gson().fromJson(json, clazz);
            }
        }

    }


}
