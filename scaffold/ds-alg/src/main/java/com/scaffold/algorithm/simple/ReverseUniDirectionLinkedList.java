package com.scaffold.algorithm.simple;

import java.util.Arrays;

/**
 * Created by Karl on 2021/6/19
 **/
public class ReverseUniDirectionLinkedList {
    public static void main(String[] args) {
        UniDirectionLinkedList list = new UniDirectionLinkedList();
        for (int i = 0; i < 10; i++) {
            UniDirectionLinkedList.UniDirectionNode node = new UniDirectionLinkedList.UniDirectionNode();
            node.setValue(i + 1);
            list.add(node);
        }
        printNodesValue(list.getHead());
        UniDirectionLinkedList.UniDirectionNode node = new ReverseUniDirectionLinkedList().reverseLinkedList(list.getHead());
        System.out.println("after reverse :");
        printNodesValue(node);
    }

    private static void printNodesValue(UniDirectionLinkedList.UniDirectionNode node) {
        while(node != null){
            System.out.println("node value = " + node.getValue());
            node = node.getNext();
        }
    }

    public UniDirectionLinkedList.UniDirectionNode reverseLinkedList(UniDirectionLinkedList.UniDirectionNode node) {
        if (node == null) {
            return null;
        }
        UniDirectionLinkedList.UniDirectionNode resultHead = node;
        UniDirectionLinkedList.UniDirectionNode curNext = node.getNext();
        resultHead.setNext(null);
        while (curNext != null) {
            UniDirectionLinkedList.UniDirectionNode nextNode = curNext.getNext();
            curNext.setNext(resultHead);
            resultHead = curNext;
            curNext = nextNode;
        }
        return resultHead;
    }
}
