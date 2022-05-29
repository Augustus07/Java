package sortalg.mergesort;

import java.util.Arrays;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Mergesort.java
 * @createTime 2022年04月11日 15:40:00
 * @Description : 归并排序 最差时间复杂度O(nlogn),
 * 核心： 拆分，归并
 */
public class Mergesort {
    public static void merge(int[] data, int l, int m, int h){
        int[] temp = new int[h - l];
        int i = l, j = m, k = 0;
        while(i<m && j<h){
            if(data[i] > data[j]) temp[k++] = data[j++];
            else temp[k++] = data[i++];
        }
        while(i!=m) temp[k++] = data[i++];
        while (j!=h) temp[k++] = data[j++];
        System.arraycopy(temp,0,data,l,h-l);
    }

    public static void mergersort(int[] data, int l, int h) {
        if(h-l < 2) return;
        int m = l + (h-l)/2;
        mergersort(data,l,m);
        mergersort(data,m,h);
        merge(data,l,m,h);
    }

    public static void main(String[] args) {
        int data[] = {6,3,2,7,1,5,8,4};
        int len = data.length;
        mergersort(data, 0, len);
        System.out.println(Arrays.toString(data));
    }
}
