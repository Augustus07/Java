package sortalg.shellsort;

import java.util.Arrays;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Shellsort.java
 * @createTime 2022年04月12日 17:33:00
 * @Description : 希尔排序
 */
public class Shellsort {
    public static void shellsort(int[] data){
        int len = data.length;
        for(int gap = len/2; gap > 0; gap/=2){
            for(int i = len - gap; i<len; i++){
                for (int j = i; j >= gap ; j-=gap) {
                    if(data[j] < data[j-gap]){
                        int temp = data[j];
                        data[j] = data[j-gap];
                        data[j-gap] = temp;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int data[] = {6,3,2,7,1,5,8,4};
        shellsort(data);
        System.out.println(Arrays.toString(data));
    }

}
