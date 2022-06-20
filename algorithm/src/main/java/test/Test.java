package test;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.*;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: test
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-01  14:22
 * @Description:
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1,2);
        map.put(2,7);
        map.put(3,5);
        Set<Map.Entry<Integer, Integer>> entrye = map.entrySet();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(entrye);
        Collections.sort(list, (o1, o2) -> o2.getValue() - o1.getValue());
        for (Map.Entry<Integer, Integer> ele : list) {
            System.out.println(ele.getKey());
        }

    }
}
