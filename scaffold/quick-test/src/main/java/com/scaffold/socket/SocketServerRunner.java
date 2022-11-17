package com.scaffold.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class SocketServerRunner {
    private volatile boolean ssSwitch = true;
    private static final AtomicLong REQ_COUNT = new AtomicLong(0L);

    public void startSocketServer() throws IOException {
        ServerSocket ss = new ServerSocket(9000);
        log.info("server started");
        while (ssSwitch) {
            final Socket s = ss.accept();
            socketRunAsync(s);
        }
        log.info("close sock server");
        ss.close();
    }

    /**
     * 异步执行方法.
     *
     * @param s socket requ
     */
    private void socketRunAsync(final Socket s) throws IOException {
        final long reqCount = REQ_COUNT.incrementAndGet();
        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        try {
            DataInputStream reader = new DataInputStream(is);
            log.info("read data from cliet");
            String line = reader.readUTF();
            log.info("request is:{}", line);
            final DataOutputStream osw = new DataOutputStream(os);
            log.info("start to write data to client");
            osw.writeUTF(String.format("hello from socket server for req:%d", reqCount));
            osw.flush();
            s.shutdownOutput();
        } catch (IOException e) {
            log.error("socketRunAsync error:{}", s.toString(), e);
        } finally {
//            if (is != null) {
//                is.close();
//            }
//            if (os != null) {
//                os.close();
//            }
//            if (s != null) {
//                try {
//                    s.close();
//                } catch (IOException e) {
//                    log.error("clock socket for {} error,", reqCount, e);
//                }
//            }
        }
        CompletableFuture.runAsync(() -> {
        });
    }

    public static void main(String[] args) throws IOException {
        new SocketServerRunner().startSocketServer();
    }
}
