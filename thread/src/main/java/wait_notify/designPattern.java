package wait_notify;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// 有一个结果需要从一个线程传递到另一个线程，让两个线程关联同一个GuardedObject
@Slf4j(topic = "c.designPattern")
public class designPattern {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();

        new Thread(()->{
           log.debug("等待结果");
           List<String> list = (List<String>) guardedObject.get();
           log.debug("结果大小：{}",list.size());
        },"t1").start();

        new Thread(()->{
            log.debug("执行下载");
            try {
                List<String> list = Downloader.download();
                guardedObject.complete(list);
            } catch (IOException e) {
                System.out.println("download error");
            }
        },"t2").start();


    }

}

class GuardedObject{

//    结果
    private Object response;

//    获取结果
    public Object get(){
        synchronized (this){
//            没有结果
            while(response == null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println("wait error");
                }
            }
        }
        return response;
    }

//    产生结果
    public void complete(Object response){
        synchronized (this){
//            给结果成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
}

class Downloader{
    public static List<String> download() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://baidu.com").openConnection();
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))){
            String line;
            while((line = reader.readLine()) != null){
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("conn.getInputStream error");
        }
        return lines;
    }
}