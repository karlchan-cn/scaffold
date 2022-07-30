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

    CompletableFuture<String> greetingWithOneWordAsync();
}
