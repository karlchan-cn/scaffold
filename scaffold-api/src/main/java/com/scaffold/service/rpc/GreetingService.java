package com.scaffold.service.rpc;

import java.util.concurrent.CompletableFuture;

/**
 * say hello service definition.
 */
public interface GreetingService {
    /**
     * greeting with one word return.
     *
     * @return word to be returned.
     */
    String greetingWithOneWord();

    /**
     * 睡眠1000ms后返回.
     *
     * @return
     */
    String greetingSleepWithOneSeconde();

    /**
     * 睡眠500ms后返回.
     *
     * @return
     */
    String greetingSleepWith500MS();

    CompletableFuture<String> greetingWithOneWordAsync();
}
