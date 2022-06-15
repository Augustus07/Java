package sortAlgorithm.bucketsort;

import java.util.Arrays;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Bucketsort.java
 * @createTime 2022年04月12日 16:24:00
 * @Description : 桶排序时间复杂度 Θ(n)
 * 桶排序是所有排序算法中最快、也是最简单的排序算法。基本思想是在知道所有待排元素的范围后，
 * 准备和这个范围同样数量的桶，并将元素放在对应的桶中，如待排元素为｛3,1,5,9,6,5,0｝，
 * 就要准备10个桶标号为0到9（代码中对应一个数组的下标），将每个元素放入对应桶中，再将所有
 * 元素按顺序输出（代码中则按顺序将数组i下标输出arrary[i]次），即为{0,1,3,5,5,6,9}
 */
public class Bucketsort {

    public static void bucketsort(int[] data){
        int len = data.length, max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            max = Math.max(max,data[i]);
        }
        int[] bucket = new int[max+1];
        for (int i = 0; i < len; i++) {
            bucket[data[i]]++;
        }
        int index = 0;
        for (int i = 0; i < max+1; i++) {
            if (bucket[i] != 0) {
                for (int j = 0; j < bucket[i]; j++) {
                    data[index++] = i;
                }
            }
        }

    }

    public static void main(String[] args) {
        int[] num = { 2,5,6,8,5,2,9,6};
        bucketsort(num);
        System.out.println(Arrays.toString(num));
    }
}
