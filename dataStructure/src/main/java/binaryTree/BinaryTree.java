package binaryTree;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: binaryTree
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-16  15:06
 * @Description:
 * @Version: 1.0
 */
public class BinaryTree {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
            val = 0;
            left = null;
            right = null;
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

    }

}
