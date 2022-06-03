package javaConcurrentUtil.concurrentHashMap;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.concurrentHashMap
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-03  20:58
 * @Description:
 * @Version: 1.0
 */
public class TestConcurrentHashMap {

    static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        //生成测试数据
//        generateTestData();

        demo(
//                创建 map 集合
//                创建 ConcurrentHashMap 对不对
                () -> new HashMap<String, Integer>(),

                (map, words) -> {
                    for (String word : words) {
                        Integer counter = map.get(word);
                        int newValue = counter == null ? 1 : counter + 1;
                        map.put(word, newValue);
                    }
                }
        );

        demo(
//                创建 map 集合
//                创建 ConcurrentHashMap 对不对
                () -> new ConcurrentHashMap<String, Integer>(),

                (map, words) -> {
                    for (String word : words) {
                        //既有get又有Put,虽然这两个操作是原子的，但是这两个操作的组合就不是原子的
                        Integer counter = map.get(word);
                        int newValue = counter == null ? 1 : counter + 1;
                        map.put(word, newValue);
                    }
                }
        );

        demo(
//                创建 map 集合
//                创建 ConcurrentHashMap 对不对
                () -> new ConcurrentHashMap<String, Integer>(),

                (map, words) -> {
                    for (String word : words) {
                        synchronized (map) {
                            //既有get又有Put,虽然这两个操作是原子的，但是这两个操作的组合就不是原子的
                            Integer counter = map.get(word);
                            int newValue = counter == null ? 1 : counter + 1;
                            map.put(word, newValue);
                        }
                    }
                }
        );

        demo(
//                创建 map 集合
//                创建 ConcurrentHashMap 对不对
                () -> new ConcurrentHashMap<String, LongAdder>(),

                (map, words) -> {
                    for (String word : words) {
//                        map.computeIfAbsent()方法保证get和put整个是原子的
                        LongAdder value = map.computeIfAbsent(word, (key) -> new LongAdder());
//                        LongAdder保证累加操作原子
                        value.increment();
                    }
                }
        );

    }

    private static <V> void demo(Supplier<Map<String, V>> supplier, BiConsumer<Map<String, V>, List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(counterMap);
    }

    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("./thread/tmp/" + i + ".txt")))) {
            while (true) {
                String word = in.readLine();
                if (word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateTestData() {
        int length = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(length * count);
        for (int i = 0; i < length; i++) {
            char ch = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(ch));
            }
        }
        Collections.shuffle(list);
//        System.out.println(System.getProperty("user.dir"));
        for (int i = 0; i< 26; i++) {
            try (PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(".\\thread\\tmp\\" + (i+1) + ".txt")))) {
                String collect = list.subList(i * count, (i + 1) * count).stream()
                        .collect(Collectors.joining("\n"));
                out.print(collect);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
