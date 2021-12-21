package com.scaffold.algorithm.simple;

import lombok.val;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Karl on 2021/7/14
 **/
public class RetrivalDenpendency implements Function<String, RetrivalDenpendency.Node> {
    public static void main(String[] args) {
        final LinkedList<List<String>> param = new LinkedList<>();
        param.add(Stream.of("A", "B").collect(Collectors.toList()));
        param.add(Stream.of("A", "C").collect(Collectors.toList()));
        param.add(Stream.of("B", "D").collect(Collectors.toList()));
        param.add(Stream.of("D", "A").collect(Collectors.toList()));


        final RetrivalDenpendency runner = new RetrivalDenpendency();
        System.out.printf("%b", runner
                .duplicatedDependencied(param));
    }

    @Override
    public Node apply(String s) {
        Node node = new Node();
        node.name = s;
        return node;
    }


    static class Node {
        int val;
        String name;
        Set<Node> nexts = new HashSet<>();
    }

    public void insertNode(Node dummyHead, String nodeName) {
        getExistNode(dummyHead, nodeName);
    }

    public Node getExistNode(Node node, String nodeName) {
        if (node == null) {
            return null;
        }
        if (node.name.equals(nodeName)) {
            return node;
        }
        if (node.nexts.size() > 0) {
            Node existNode = getExistNode(node, nodeName);
            if (existNode != null) {
                return existNode;
            }
        }
        return null;
    }

    public boolean duplicatedDependencied(List<List<String>> dependencyList) {
        if (dependencyList == null || dependencyList.size() <= 0) {
            return false;
        }
        RetrivalDenpendency fun = new RetrivalDenpendency();
        Map<String, Node> nodeCache = new HashMap<>();
        for (List<String> strings : dependencyList) {
            String firEle = strings.get(0);
            String secEle = strings.get(1);
            Node node = nodeCache.computeIfAbsent(firEle, fun);
            Node subNode = nodeCache.computeIfAbsent(secEle, fun);
            node.nexts.add(subNode);
        }
        for (Map.Entry<String, Node> entry : nodeCache.entrySet()) {
            Map<Node, AtomicInteger> countMap = new HashMap<>();
            if (countHitted(entry.getValue(), countMap)) {
                return true;
            }
        }
        return false;
    }

    private boolean countHitted(Node node, Map<Node, AtomicInteger> countMap) {
        final AtomicInteger count = countMap.computeIfAbsent(node, (Node key) -> new AtomicInteger());
        if (count.incrementAndGet() > 1) {
            return true;
        }
        for (Node next : node.nexts) {
            if (countHitted(next, countMap)) {
                return true;
            }
        }
        return false;
    }
}
