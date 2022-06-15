package com.scaffold.highperformance;

import com.lmax.disruptor.EventHandler;

/**
 * Created by Karl on 2021/12/22
 **/
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("longEvent = " + longEvent + ", l = " + l + ", b = " + b);
    }
}
