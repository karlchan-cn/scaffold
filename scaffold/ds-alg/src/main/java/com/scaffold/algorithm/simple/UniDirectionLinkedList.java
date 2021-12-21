package com.scaffold.algorithm.simple;

import lombok.Data;

/**
 * 单方向链表
 * Created by Karl on 2021/6/19
 **/
@Data
public class UniDirectionLinkedList {
    /**
     * 长度
     */
    private int size;
    /**
     * 首元素
     */
    private UniDirectionNode head;
    /**
     * 尾巴
     */
    private UniDirectionNode tail;

    public boolean add(UniDirectionNode node) {
        if (node == null) {
            return false;
        }
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        return true;

    }

    @Data
    public static class UniDirectionNode {
        /**
         * 节点值
         */
        private int value;
        /**
         * 下一个节点
         */
        private UniDirectionNode next;
    }
}
