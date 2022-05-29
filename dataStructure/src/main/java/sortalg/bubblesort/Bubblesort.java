package sortalg.bubblesort;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Bubblesort.java
 * @createTime 2022年04月11日 14:09:00
 * @Description : 冒泡排序：时间复杂度 O(n^2)
 */
public class Bubblesort {
    public static void main(String[] args) {
        int data[] = {5,2,7,4,6,3,1};

        int n = data.length;
        long start = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            for(int j=0; j<n-i-1; j++){
                if(data[j]>data[j+1]){
                    int temp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = temp;
                }
            }
        }
        long end = System.currentTimeMillis();

        for(int i : data){
            System.out.println(i);
        }


    }
}
