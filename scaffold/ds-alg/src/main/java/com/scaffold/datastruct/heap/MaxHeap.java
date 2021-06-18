package com.scaffold.datastruct.heap;

import java.util.ArrayList;
import java.util.Collections;

/**
 * data-struct heap
 * Created by 80166776 on 2021/6/12
 **/
public class MaxHeap<E extends Comparable> {
    private ArrayList<E> entries;

    public MaxHeap(int size) {
        this.entries = new ArrayList<>();
    }

    private int parent(int childIdx) {
        if (childIdx == 0) {
            return childIdx;
        }
        return (childIdx - 1) / 2;
    }

    public void add(E e) {
        entries.add(e);
        shiftUp(entries.size() - 1);
    }

    private void shiftUp(int k) {
        int parentK;
        while (k > 0 && entries.get((parentK = parent(k))).compareTo(entries.get(k)) < 0) {
            // 父亲节点小于子节点,交换
            Collections.swap(entries, k, parentK);
            k = parentK;
        }
    }
    private void shiftDown(int k){
        // 1.判断当前节点是否有子节点。下沉到叶节点，就没有儿子了，就不用下沉了
        // 注：因为leftChild索引肯定比rightChild小，所以只要有leftChild就有子节点
        while (leftChild(k) < entries.size()) {
            // 2.拿到leftChild与rightChild的大值
            int j = leftChild(k);
            if (j + 1 < entries.size() && entries.get(j + 1).compareTo(entries.get(j)) > 0) {
                j = rightChild(k);
            }

            // 3.判断子节较大值是否大于自己（父节点）
            if (entries.get(k).compareTo(entries.get(j)) >= 0) {
                break;
            }

            // 4.若大于，交换数组中两节点位置
            Collections.swap(entries, k, j);

            // 更新父节点，进行下一轮下沉
            k = j;
        }


    }

    /**
     * 左子节点索引.
     *
     * @param idx
     * @return
     */
    private int leftChild(int idx) {
        return idx * 2 + 1;
    }

    /**
     * 右子节点索引.
     *
     * @param idx
     * @return
     */
    private int rightChild(int idx) {
        return idx * 2 + 2;
    }
}
