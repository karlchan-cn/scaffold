package com.scaffold.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * Created by Karl on 2021/12/27
 **/
public class ByteBufferRunner {

    public void readFile() throws IOException {
        RandomAccessFile raf = getClassRaf();
        FileChannel inChannel = raf.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("read:" + bytesRead + ",position:" + buf.position() +
                    " limit:" + buf.limit() + " capacity:" + buf.capacity());
            buf.flip();
            System.out.println("after flip read:" + bytesRead + ",position:" + buf.position() +
                    " limit:" + buf.limit() + " capacity:" + buf.capacity());
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        inChannel.close();
        raf.close();
    }

    /**
     * 获取当前class文件的file,后续使用谨记close.
     *
     * @return 当前可访问文件
     * @throws FileNotFoundException file not found
     */
    private RandomAccessFile getClassRaf() throws FileNotFoundException {
        return new RandomAccessFile(getClass().getResource("").getPath() + "/ByteBufferRunner.class", "rw");
    }

    public void putToBuffer() {
        LongBuffer lb = LongBuffer.allocate(48);
        for (int i = 0; i < 58; i++) {
            lb.put(i);
        }
        lb.flip();
        while (lb.hasRemaining()) {
            System.out.print("value:" + lb.get());
        }
    }

    public void putToDirectByteBuffer() {
        final ByteBuffer bb1G = MappedByteBuffer.allocateDirect(1024 * 1024 * 200);
        final ByteBuffer heapBuff = MappedByteBuffer.allocate(1024);
        bb1G.clear();
        heapBuff.clear();
    }


    /**
     * filechannel进行文件复制（零拷贝）
     */
    public void fileCopyWithFileChannel(File fromFile) {
        File toFile = new File(getClass().getResource("").getPath() + "/copy-class.class.class");
        try (// 得到fileInputStream的文件通道
             FileChannel fileChannelInput = new FileInputStream(fromFile).getChannel();
             // 得到fileOutputStream的文件通道
             FileChannel fileChannelOutput = new FileOutputStream(toFile).getChannel()) {
            //将fileChannelInput通道的数据，写入到fileChannelOutput通道
            fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static final int BUFFER_SIZE = 1024;

    /**
     * BufferedInputStream进行文件复制（用作对比实验）
     */
    public void bufferedCopy(File fromFile) throws IOException {
        File toFile = new File(getClass().getResource("").getPath() + "/copy-class.class.class");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fromFile));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(toFile))) {
            byte[] buf = new byte[BUFFER_SIZE];
            while ((bis.read(buf)) != -1) {
                bos.write(buf);
            }
        }
    }

    /**
     * 使用直接内存映射读取文件
     */
    public void fileReadWithMmap(File file) {
        //long begin = System.currentTimeMillis();
        byte[] b = new byte[BUFFER_SIZE];
        int len = (int) file.length();
        MappedByteBuffer buff;
        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            // 将文件所有字节映射到内存中。返回MappedByteBuffer
            buff = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

            for (int offset = 0; offset < len; offset += BUFFER_SIZE) {
                if (len - offset > BUFFER_SIZE) {
                    buff.get(b);
                } else {
                    buff.get(new byte[len - offset]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       // long end = System.currentTimeMillis();
        //System.out.println("time is:" + (end - begin));
    }

    /**
     * HeapByteBuffer读取文件
     */
    public void fileReadWithByteBuffer(File file) {
        //long begin = System.currentTimeMillis();
        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            // 申请HeapByteBuffer
            ByteBuffer buff = ByteBuffer.allocate(BUFFER_SIZE);
            while (channel.read(buff) != -1) {
                buff.flip();
                buff.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //long end = System.currentTimeMillis();
        //System.out.println("time is:" + (end - begin));
    }

    public static void main(String[] args) throws Exception {
        final ByteBufferRunner fbr = new ByteBufferRunner();
        fbr.readFile();
        fbr.putToBuffer();
        String name = "1";
        final byte[] bytes = "name".getBytes();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);

        System.out.println("name's length: " + name.getBytes(StandardCharsets.UTF_8).length);
    }
}
