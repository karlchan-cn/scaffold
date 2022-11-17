package com.scaffold.alg.lintcode.bloomberg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2022/11/3
 **/
public class KillProcess {


    private class Node {
        public Node(Integer pid) {
            this.pid = pid;
            children = new ArrayList<>();
        }

        List<Node> children;
        Integer pid;
    }
    /**
     * @param pid:  the process id
     * @param ppid: the parent process id
     * @param kill: a PID you want to kill
     * @return: a list of PIDs of processes that will be killed in the end
     * we will sort your return value in output
     */
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int i = 0; i < pid.size(); i++) {
            Integer p = pid.get(i);
            Integer pp = ppid.get(i);
            Node node = nodeMap.get(p);
            if (node == null) {
                node = new Node(p);
                nodeMap.put(p, node);
            }
            if (pp != 0) {
                Node pnode = nodeMap.get(pp);
                if (pnode == null) {
                    pnode = new Node(pp);
                    nodeMap.put(pp, pnode);
                }
                pnode.children.add(node);
            }
        }
        final Node nodeToKill = nodeMap.get(kill);
        List<Integer> result = new ArrayList<>();
        result.add(nodeToKill.pid);
        addChildren(nodeToKill, result);
        return result;
    }

    private void addChildren(Node node, List<Integer> result) {
        for (Node child : node.children) {
            result.add(child.pid);
            addChildren(child, result);
        }
    }
}
