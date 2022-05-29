package cn.itcast.jvm.t3.load;

public class Load5_3 {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(Load5_3.class.getClassLoader());
        Class<?> aClass = Load5_3.class.getClassLoader().loadClass("cn.itcast.jvm.t3.load.H");
        System.out.println(aClass.getClassLoader());

    }
}

