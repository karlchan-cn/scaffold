package com.scaffold.algorithm.simple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Karl on 2021/6/25
 **/
public class TreeTravell {
    class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;
    }

    class Solution {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> res = new LinkedList<>();
            preorder(root, res);
            return res;
        }

        public void preorder(TreeNode root, List<Integer> res) {
            if (root == null) {
                return;
            }
            res.add(root.val);
            preorder(root.left, res);
            preorder(root.right, res);
        }
    }


}
