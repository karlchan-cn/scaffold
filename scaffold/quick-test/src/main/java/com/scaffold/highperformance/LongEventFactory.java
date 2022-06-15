package com.scaffold.highperformance;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Karl on 2021/12/22
 **/
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
