package com.scaffold.nio;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Karl on 2021/12/28
 **/
class ByteBufferRunnerTest {

    ByteBufferRunner bbr = new ByteBufferRunner();

    @JunitPerfConfig(duration = 5_000)
    //@Test
    public void testZeroCopy() {
        for (int i = 0; i < 10000; i++) {
            bbr.fileCopyWithFileChannel(getCurrentClassFile());
        }

    }

    private File getCurrentClassFile() {
        final File file = new File(getClass().getResource("").getPath() + "/ByteBufferRunnerTest.class");
        return file;
    }

    @JunitPerfConfig(duration = 5_000)
    //@Test
    public void testBufferedCopy() throws IOException {
        for (int i = 0; i < 10000; i++) {
            bbr.bufferedCopy(getCurrentClassFile());
        }
    }

    @JunitPerfConfig(duration = 5_000)
    public void testFileReadWithMmap() {
        for (int i = 0; i < 10000; i++) {
            bbr.fileReadWithMmap(getCurrentClassFile());
        }
    }

    @JunitPerfConfig(duration = 5_000)
    public void testFileReadWithByteBuffer() {
        for (int i = 0; i < 10000; i++) {
            bbr.fileReadWithByteBuffer(getCurrentClassFile());
        }
    }


}