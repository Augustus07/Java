package sortAlgorithm.insertionsort;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Insertionsort.java
 * @createTime 2022年04月11日 15:01:00
 * @Description : 插入排序， 即打麻将理牌,
 * 假设需要从小到大， 若源数列从小到大，则有最小的时间复杂度O(n);若原数列从大到小，则有最大的时间复杂度O(n^2).
 * 平均时间复杂度O(n^2)
 */
public class Insertionsort {
    public static void main(String[] args) {
        int data[] = {5,2,7,4,6,3,1};

        int n = data.length;
        for(int i=1; i<n; i++){
            int j = i -1, temp = data[i];
            for(; j>=0; j--){
                if(data[j] > temp) {
                    data[j+1] = data[j];
                }else {
                    break;
                }
            }
            data[j+1] = temp;
        }

        for(int i: data) System.out.println(i);

    }
}
