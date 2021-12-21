package com.scaffold.datastruct.ringbuffer;

/**
 * Created by Karl on 2021/11/23
 **/
public class RingBuffer<T> {
    private static final int DEFAULT_SIZE = 16;
    private T[] data;
    private volatile int readSeq;
    private volatile int writeSeq;

    public RingBuffer(int size) {
        if (size <= 0) {
            size = DEFAULT_SIZE;
        }
        data = (T[]) new Object[size];
        this.readSeq = 0;
        this.writeSeq = -1;
    }

    /**
     * 加入新元素
     *
     * @param param
     * @return
     */
    public boolean offer(T param) {
        //队列已经满
        if (writeSeq - readSeq + 1 == data.length) {
            return false;
        }
        int nextWriteSeq = writeSeq + 1;
        data[nextWriteSeq % data.length] = param;
        writeSeq++;
        return true;
    }

    /**
     * 读取数据
     *
     * @return
     */
    public T poll() {
        if (readSeq >= writeSeq) {
            return null;
        }
        T result = data[readSeq % data.length];
        readSeq++;
        return result;
    }

    /**
     * 当前容量.
     *
     * @return
     */
    public int size() {
        return writeSeq - readSeq + 1;
    }
}
