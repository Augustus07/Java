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

    static class ListNode {
        int val;
        ListNode next;

        public ListNode () {}
        public ListNode (int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        node1.next = node2;

        for (int i = 0; i < 3; i++) {
            System.out.println(node1.val);
            node1 = node1.next;
        }

    }
}
