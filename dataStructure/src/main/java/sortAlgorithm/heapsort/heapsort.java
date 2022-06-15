package sortAlgorithm.heapsort;

import java.util.Arrays;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName heapsort.java
 * @createTime 2022年04月11日 18:40:00
 * @Description : 堆排序： 1.建堆(从第一个非叶节点开始，到第一个元素为止，依次下滤建堆)
 * 2.堆中首元素和尾元素，交换后尾元素默认有序 3.继续调整堆顶元素，转2
 * 时间复杂度O(nlogn) , 建堆O（n）,n个元素，下滤一次操作log（n）,所以总的时间复杂度 n + nlog（n）
 */
public class heapsort {

//    调整大顶堆中的某个元素,具体思想是，将该元素下滤，即若要构建大顶堆，如果当前元素比他两个孩子中最大的要小，就将大的那个孩子
//    调整至父亲的位置，依次向下，直到找到该元素应该所在的位置
    public static void adjustheap(int[] data,int i, int len) {
        int temp = data[i];
        for(int k= i*2+1; k< len; k=k*2+1){
            if(k+1<len && data[k] < data[k+1]) {
                k = k + 1;
            }
            if(temp < data[k]){
                data[i] = data[k];
                i = k;
            }else{
                break;
            }
        }
        data[i] = temp;
    }

    public static void main(String[] args) {
        int data[] = {6,3,2,7,1,5,8,4};
        int len = data.length;
//      对于该完全二叉树，从第一个非叶子节点，第二个非叶子节点，。。。，根节点向下调整
//        该循环结束后，数组从逻辑上满足堆的性质,建堆的时间复杂度O（n）
        for(int i = (len - 1)/2; i>=0; i--){
            adjustheap(data,i,len);
        }
        for(int i = len-1; i>0; i--){
//            交换堆中第一个元素和最后一个元素
            int temp = data[i];
            data[i] = data[0];
            data[0] = temp;
//            交换后只有首元素不满足大顶堆的性质，只需队首元素向下调整
            adjustheap(data,0, i);
        }
        System.out.println(Arrays.toString(data));

    }
}
