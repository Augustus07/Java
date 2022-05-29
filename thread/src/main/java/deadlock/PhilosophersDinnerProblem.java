package deadlock;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "PhilosophersDinnerProblem")
public class PhilosophersDinnerProblem {
    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        new Philosopher("苏",c1,c2).start();
        new Philosopher("柏",c2,c3).start();
        new Philosopher("亚",c3,c4).start();
        new Philosopher("赫",c4,c5).start();
        new Philosopher("阿",c5,c1).start();
    }
}

@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread{
    Chopstick left;
    Chopstick right;

    public Philosopher(String name,Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true){
            synchronized (left){
                synchronized (right){
                    eat();
                }
            }
        }
    }

    public void eat(){
        log.debug("eating");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
    }
}

@Slf4j(topic = "Chopstick")
class Chopstick{
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Chopstick{" +
                "name='" + name + '\'' +
                '}';
    }
}
