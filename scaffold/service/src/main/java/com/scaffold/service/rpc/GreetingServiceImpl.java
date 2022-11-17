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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * vm param:-XX:+UseG1GC -Xmx512M -XX:MaxGCPauseMillis=200 -Xlog:gc*=debug
 * greeting interface default implementation.
 *
 * @author 80166776
 */
@Slf4j
@DubboService(registry = {"innerRegistry"}, group = "${biz.type:local}.irg", version = "1.0.0", protocol = "dubbo",
        timeout = 100, retries = 0, actives = 1024, executes = 1024, delay = -1
        //, connections = 1
)
public class GreetingServiceImpl extends ChannelInboundHandlerAdapter implements GreetingService {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    public static Executor es = null;

    @Override
    public String greetingWithOneWord() {
        final String result = "hello from user:" + COUNTER.getAndIncrement();
        try {
            Thread.sleep(1000);
            System.out.println(result);
        } catch (InterruptedException e) {
            log.error("");
        }
        //log.info("return greeting with hello");
        return result;
    }

    public String greetingSleepWithParamMs(long sleepMs) {
        final long start = System.currentTimeMillis();
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            log.error("");
        }
        String result = "hello from user:" + COUNTER.getAndIncrement() + ", with timeout ms:" + sleepMs + ", cost:" + (System.currentTimeMillis() - start) + "ms";
        log.info(result);
        return result;
    }

    @Override
    public String greetingSleepWithOneSeconde() {
        return greetingSleepWithParamMs(1000L);
    }

    @Override
    public String greetingSleepWith500MS() {
        return greetingSleepWithParamMs(500L);
    }

    @Override
    public CompletableFuture<String> greetingWithOneWordAsync() {
        log.info("greetingWithOneWordAsync threadInfo:{}|{}", Thread.currentThread().getId(), Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("greet supplyAsync threadInfo:{}|{}", Thread.currentThread().getId(), Thread.currentThread().getName());
                // sleep 10 second
                Thread.sleep(5_000L);
            } catch (InterruptedException e) {
                log.error("greetingWithOneWordAsync sleep error", e);
            }
            return greetingWithOneWord();
        });
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
