package com.scaffold.heracles.enums;

/**
 * Created by Karl on 2022/3/3
 **/
public enum ConfType {
    REDIS("redis", "redis"),
    ZK("zookeeper", "zk|zookeeper|registry"),
    MQ("mq", "rocket|kafka"),
    MYSQL("mysql", "mysql"),
    JINS("jins", "jins");

    private ConfType(String name, String exp) {
        this.exp = exp;
        this.name = name;
    }

    private String name;
    private String exp;

    public String getName() {
        return name;
    }

    public String getExp() {
        return exp;
    }
}
