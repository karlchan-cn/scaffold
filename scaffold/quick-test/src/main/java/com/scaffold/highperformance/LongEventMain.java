package com.scaffold.highperformance;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * Created by Karl on 2021/12/22
 **/
public class LongEventMain {
    public static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println(event);
    }

    public static void translate(LongEvent event, long sequence, ByteBuffer buffer) {
        event.setValue(buffer.getLong(0));
    }

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith((event, sequences, endOfBath) ->
                System.out.println("longEvent = " + event + ", l = " + sequences + ", b = " + endOfBath));
        final RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            byteBuffer.putLong(0, l);
            //ringBuffer.publishEvent(((longEvent, l1, buff) -> longEvent.setValue(byteBuffer.getLong(0))));
            ringBuffer.publishEvent(LongEventMain::translate,byteBuffer);
            Thread.sleep(1000);
        }
    }
}
