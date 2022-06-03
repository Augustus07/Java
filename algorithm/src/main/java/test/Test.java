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
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        int a = 0, b = 0;
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            a = nums[i];
            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                b = nums[j];
                int left = j + 1, right = nums.length - 1;
                while (left < right) {
                    int sum = a + b + nums[left] + nums[right];
                    if (sum < target) {
                        left++;
                    } else if (sum > target) {
                        right--;
                    } else {
                        res.add(Arrays.asList(a, b, nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    }
                }
            }
        }
        return res;

    }

    public static void main(String[] args) {
        int[] data = {2,2,2,2,2};
        fourSum(data, 8);
    }
}
