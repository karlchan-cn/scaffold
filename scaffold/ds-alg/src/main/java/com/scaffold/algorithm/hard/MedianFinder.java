package com.scaffold.algorithm.hard;

import java.util.PriorityQueue;

/**
 * 前置知识：
 * 需要找到堆排序的知识。
 * 比如，大顶堆：这堆中（一般是二叉树结构），根节点的元素时最大的，对左右节点的大小没有限制。严格的定义是所有的父节点元素比子节点元素要大。小顶堆同理。
 * 找到这个前置知识应该就能解决这道题目了。另外，在java中，这种堆结构使用PriorityQueue优先队列的结构。
 * 解题思路：
 * 一个数据结构没有办法实现求一个流的中位数，所以需要两个优先级队列。
 * 需要维护这另个队列的元素数量差小于等于1，这样求中位数直接拿堆顶元素即可。
 * 注意：大顶堆存储流的小元素，取值取堆中的大元素，也就是中间的位置。小顶堆存储大元素，取值取堆顶，大中的小元素，也是中间段。同时，
 * 需要确保小顶堆存储的大元素都比大顶堆存储的小元素都要大。这样才能确保去中位数的时候是正确的。
 * 总结：
 * 把流的数据分成两堆，一堆大的，一堆小的，大堆的数据都比小堆的大。同时两堆的数量差控制在1以内。求中位数的时候，直接拿大堆的最小元素，和小堆的最大元素，就可以求出中位数。这也就是大的堆需要用小顶堆求最小的元素的原因。小堆同理。
 * 代码实现细节：
 * ① getMedian：如果两个堆的元素一样，那么去两个堆顶元素的平均值，否则取数量多的堆的堆顶元素即可。
 * ② add：根据两个堆的数量，决定要把元素添加到哪个堆里面
 * ​	 比如：大的堆数量多，则需要把元素添加到小的堆里面。
 * ​	注意：这个时候不能直接添加到小的堆里面，因为这样没有办法确保小的堆的所有元素都比大堆的元素要小，那么获取中位数就会出现问题。
 * 所以需要一个技巧，先加入到大顶堆，然后取栈顶元素，大顶堆的使用的是小顶堆的数据结构，所以堆顶的元素是大元素中最小的元素，把它加入到小的元素，就不会出现上面的问题。具体细节看代码实现。十分巧妙。
 */

class MedianFinder {
    private PriorityQueue<Integer> large;
    private PriorityQueue<Integer> small;

    public MedianFinder() {
        // 小顶堆
        large = new PriorityQueue<>();
        // 大顶堆
        small = new PriorityQueue<>((a, b) -> {
            return b - a;
        });
    }

    public double findMedian() {
        // 如果元素不一样多，多的那个堆的堆顶元素就是中位数
        if (large.size() < small.size()) {
            return small.peek();
        } else if (large.size() > small.size()) {
            return large.peek();
        }
        // 如果元素一样多，两个堆堆顶元素的平均数是中位数
        return (large.peek() + small.peek()) / 2.0;
    }

    public void addNum(int num) {
        if (small.size() >= large.size()) {
            small.offer(num);
            large.offer(small.poll());
        } else {
            large.offer(num);
            small.offer(large.poll());
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
