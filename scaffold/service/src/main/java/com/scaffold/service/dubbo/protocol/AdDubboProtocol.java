package com.scaffold.service.dubbo.protocol;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AdDubboProtocol extends DubboProtocol {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdDubboProtocol.class);
    /**
     * 父类的optimizeSerialization方法,用作访问父类方法.
     */
    private static Method OPTIMIZE_METHOD = null;
    /**
     * 父类的getClients方法,用作访问父类方法.
     */
    private static Method GETCLIENT_METHOD = null;

    static {
        try {
            OPTIMIZE_METHOD = DubboProtocol.class.getDeclaredMethod("optimizeSerialization", URL.class);
            GETCLIENT_METHOD = DubboProtocol.class.getDeclaredMethod("getClients", URL.class);
            OPTIMIZE_METHOD.setAccessible(true);
            GETCLIENT_METHOD.setAccessible(true);
        } catch (NoSuchMethodException e) {
            LOGGER.error("reflection got error");
        }
    }

    @Override
    public <T> Invoker<T> protocolBindingRefer(Class<T> serviceType, URL url) throws RpcException {
        // optimizeSerialization(url);
        ExchangeClient[] clients = null;
        try {
            OPTIMIZE_METHOD.invoke(this, url);
            clients = (ExchangeClient[]) GETCLIENT_METHOD.invoke(this, url);
        } catch (IllegalAccessException e) {
            LOGGER.error("optimizeSerialization access error:", e);
            throw new RpcException(e);
        } catch (InvocationTargetException e) {
            LOGGER.error("optimizeSerialization invocation error:", e);
            throw new RpcException(e);
        }
        // create rpc invoker.
        AdDubboInvoker<T> invoker = new AdDubboInvoker<>(serviceType, url, clients, invokers);
        invokers.add(invoker);
        return invoker;
    }
}
