package Immutable;

import lombok.extern.slf4j.Slf4j;
import sun.font.FontRunIterator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Slf4j(topic = "c.test01")
public class test01 {

    public static void main(String[] args) {
        DateTimeFormatter stf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        test();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                TemporalAccessor parse = stf.parse("1951-04-21");
                log.debug("{}",parse);
            }).start();
        }
    }

    private static void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                synchronized (sdf){
                    try {
                        log.debug("{}",sdf.parse("1951-04-21"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
