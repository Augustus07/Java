package sortalg.quicksort;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Quicksort.java
 * @createTime 2022年04月11日 17:15:00
 * @Description : 快速排序，当从小到大或从大到小的时候，有时间复杂度n^2,当平均分布时，有时间复杂度nlogn
 * 核心： 选轴点，轴点左边比它小，轴点右边比他大，然后排序轴点的左边，排序轴点的右边
 */

public class Quicksort {
    public static void quicksort(int[] data, int l, int h){
        if(l>h) return;
        int i = l, j = h, save = data[i], time = 0, temp;
        while(i<j){
            if(time%2 == 0){
                while(data[j]>= save && i<j) {
                    j--;
                }
                time++;
            }else{
                while (data[i]<save && i<j) {
                    i++;
                }
                time++;
            }
            temp = data[j];
            data[j] = data[i];
            data[i] = temp;
        }
        data[i] = save;
        quicksort(data,l,i-1);
        quicksort(data,i+1, h);
    }

    public static void main(String[] args) {
        int data[] = {6,3,2,7,1,5,8,4};
        int len = data.length;
        quicksort(data, 0, len - 1);
        for(int i : data) System.out.println(i);
    }
}
