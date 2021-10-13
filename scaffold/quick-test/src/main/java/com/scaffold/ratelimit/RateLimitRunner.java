package com.scaffold.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Created by Karl on 2021/7/21
 **/
public class RateLimitRunner {
    public static void main(String[] args) {
        RateLimiter r = RateLimiter.create(5);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire() + "s");
        }
    }
}
