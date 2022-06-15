package linkedList;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: linkedList
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-16  15:04
 * @Description:
 * @Version: 1.0
 */
public class LinkedList {
    class Node {
        int val;
        Node next;

        public Node() {
            val = 0;
            next = null;
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

}
