package sortAlgorithm.selectionsort;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Selectionsort.java
 * @createTime 2022年04月11日 14:44:00
 * @Description : 选择排序：冒泡是最低效的选择排序，我们只需找到未排序序列中最大数的索引和未排序序列中最后一位交换即可
 */
public class Selectionsort {
    public static void main(String[] args) {
        int data[] = {5,2,7,4,6,3,1};

        int n = data.length;
        for(int i=n-1 ; i>0; i--){
            int maxindex = 0;
            for(int j=0; j<=i; j++){
                if(data[maxindex] < data[j]) maxindex = j;
            }
            int temp = data[maxindex];
            data[maxindex] = data[i];
            data[i] = temp;
        }
        for(int i: data) System.out.println(i);
    }
}
