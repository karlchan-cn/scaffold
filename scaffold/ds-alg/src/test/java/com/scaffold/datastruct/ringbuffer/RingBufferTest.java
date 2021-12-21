package com.scaffold.datastruct.ringbuffer;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Karl on 2021/11/23
 **/
public class RingBufferTest {
    @Test
    public void testOffer() {
        int size = 8;
        final RingBuffer<Object> rb = new RingBuffer<>(size);
        for (int i = 0; i < size; i++) {
            rb.offer(new Object());
        }
        assertTrue(rb.size() == size);
        assertFalse(rb.offer(new Object()));
    }

}