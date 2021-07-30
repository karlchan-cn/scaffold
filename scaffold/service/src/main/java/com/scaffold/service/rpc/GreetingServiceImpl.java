package com.scaffold.service.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * greeting interface default implementation.
 *
 * @author 80166776
 */
@Slf4j
@DubboService(registry = {"innerRegistry"}, group = "${biz.type}.irg", version = "1.0.0", protocol = {"dubbo"},
        timeout = 100, retries = 0, actives = 128, executes = 256, delay = -1, connections = 2)
public class GreetingServiceImpl extends ChannelInboundHandlerAdapter implements GreetingService {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Override
    public String greetingWithOneWord() {
        final String result = "hello from user:" + COUNTER.getAndIncrement();
        System.out.println(result);
        //log.info("return greeting with hello");
        return result;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.info("receive msg:{}", msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        log.error("msg exception.", cause);
        cause.printStackTrace();
        ctx.close();
    }

    @PreDestroy
    private void destory() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @PostConstruct
    private void posConsturct() {
        new Thread(() -> {
            bossGroup = new NioEventLoopGroup(); // (1)
            workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap(); // (2)
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class) // (3)
                        .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(this);
                            }
                        })
                        .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                        .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

                // Bind and start to accept incoming connections.
                ChannelFuture f = b.bind(8888).sync(); // (7)

                // Wait until the server socket is closed.
                // In this example, this does not happen, but you can do that to gracefully
                // shut down your server.
                f.channel().closeFuture().sync();
                log.info("start server.");
            } catch (Exception e) {
                log.error("posConsturct got error:", e);
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }).start();

    }
}
