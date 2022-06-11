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

    private static void getNext(int[] next, String s) {
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(j) != s.charAt(i)) {
                j = next[j - 1];
            }
            if (s.charAt(j) == s.charAt(i)) {
                j++;
            }
            next[i] = j;
        }
    }



    public static void main(String[] args) {
        String s = "ababac";
        int[] next = new int[s.length()];
        getNext(next, s);
        System.out.println(Arrays.toString(next));

    }
}
