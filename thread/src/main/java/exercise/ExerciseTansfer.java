package exercise;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j(topic = "c.ExerciseTansfer")
public class ExerciseTansfer {

    static Random random = new Random();

//    返回 [1,6)
    public static int randomAmount(){
        return random.nextInt(5)+1;
    }

    public static void main(String[] args) {

        TicketWindow window = new TicketWindow(10000);

        List<Thread> threadList = new ArrayList<>();

        List<Integer> amountlist = new Vector<>();

        for (int i = 0; i < 4000; i++) {
            Thread thread = new Thread(()->{
                int amount = window.sell(randomAmount());
                try {
                    Thread.sleep(randomAmount()*10);
                } catch (InterruptedException e) {
                    System.out.println("sleep error");
                }
                amountlist.add(amount);
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("join error");
            }
        }

        log.debug("余票数:{}",window.getCount());
        log.debug("卖票数:{}",amountlist.stream().mapToInt(i-> i).sum());
    }

}
class TicketWindow{
    private int count;

    public TicketWindow(int count){
        this.count = count;
    }

//    获取余票数量
    public int getCount(){
        return count;
    }

//    售票
    public int sell(int amount){

        if (this.count >= amount){
            this.count -= amount;
            return amount;
        }else {
            return 0;
        }
    }

}


