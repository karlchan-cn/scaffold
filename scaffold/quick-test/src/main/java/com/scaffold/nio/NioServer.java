package com.scaffold.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Karl on 2022/1/5
 **/
public class NioServer {
    /**
     * 响应buffer
     */
    private ByteBuffer respBuffer = ByteBuffer.allocate(1024);
    /**
     * 请求buffer
     */
    private ByteBuffer reqBuffer = ByteBuffer.allocate(1024);

    private Selector selector;

    public NioServer(int port) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        final ServerSocket socket = ssc.socket();
        socket.bind(new InetSocketAddress(port));
        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start at port port = " + port);
    }

    private void listen() throws IOException {
        while (true) {
            selector.select();
            final Set<SelectionKey> sks = selector.selectedKeys();
            final Iterator<SelectionKey> skIter = sks.iterator();
            while (skIter.hasNext()) {
                SelectionKey selectionKey = skIter.next();
                skIter.remove();
                handleKey(selectionKey);
            }
        }
    }

    private void handleKey(SelectionKey selectionKey) throws IOException {

        // 接受请求
        ServerSocketChannel server;

        SocketChannel client;

        String receiveText;

        String sendText;

        int count = 0;

        // 测试此键的通道是否已准备好接受新的套接字连接。
        if (selectionKey.isAcceptable()) {

            // 返回为之创建此键的通道。
            server = (ServerSocketChannel) selectionKey.channel();

            // 接受到此通道套接字的连接。
            // 此方法返回的套接字通道（如果有）将处于阻塞模式。
            client = server.accept();

            // 配置为非阻塞
            client.configureBlocking(false);

            // 注册到selector，等待连接
            client.register(selector, SelectionKey.OP_READ);

        } else if (selectionKey.isReadable()) {

            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();

            //将缓冲区清空以备下次读取
            reqBuffer.clear();

            //读取服务器发送来的数据到缓冲区中
            count = client.read(reqBuffer);

            if (count > 0) {

                receiveText = new String(reqBuffer.array(), 0, count);

                System.out.println("服务器端接受客户端数据--:" + receiveText);

                client.register(selector, SelectionKey.OP_WRITE);

            }

        } else if (selectionKey.isWritable()) {

            //将缓冲区清空以备下次写入
            respBuffer.clear();

            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();

            sendText = "message from server--";

            //向缓冲区中输入数据
            respBuffer.put(sendText.getBytes());

            //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
            respBuffer.flip();

            //输出到通道
            client.write(respBuffer);

            System.out.println("服务器端向客户端发送数据--：" + sendText);

            client.register(selector, SelectionKey.OP_READ);

        }

    }

    public static void main(String[] args) throws IOException {

        int port = 8080;
        NioServer server = new NioServer(port);
        server.listen();

    }
}
