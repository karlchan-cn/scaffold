package com.scaffold.service.dubbo.protocol;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.AtomicPositiveInteger;
import org.apache.dubbo.remoting.Constants;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.TimeoutException;
import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.protocol.dubbo.DubboInvoker;
import org.apache.dubbo.rpc.support.RpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.apache.dubbo.common.constants.CommonConstants.*;

public class AdDubboInvoker<T> extends DubboInvoker {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdDubboProtocol.class);
    private final String version;
    private final ExchangeClient[] clients;
    private final AtomicPositiveInteger index = new AtomicPositiveInteger();

    public static final String DYNAMIC_TIMEOUT_KEY = "dynamic-timeout";

    public AdDubboInvoker(Class<T> serviceType, URL url, ExchangeClient[] clients) {
        this(serviceType, url, clients, null);
    }

    public AdDubboInvoker(Class<T> serviceType, URL url, ExchangeClient[] clients, Set set) {
        super(serviceType, url, clients, set);
        version = initVersion(url);
        this.clients = clients;
    }

    private String initVersion(URL url) {
        // get version.
        return url.getParameter(VERSION_KEY, "0.0.0");
    }

    @Override
    protected Result doInvoke(final Invocation invocation) throws Throwable {
        RpcInvocation inv = (RpcInvocation) invocation;
        final String methodName = RpcUtils.getMethodName(invocation);
        inv.setAttachment(PATH_KEY, getUrl().getPath());
        inv.setAttachment(VERSION_KEY, version);
        ExchangeClient currentClient;
        if (clients.length == 1) {
            currentClient = clients[0];
        } else {
            currentClient = clients[index.getAndIncrement() % clients.length];
        }
        try {
            boolean isOneway = RpcUtils.isOneway(getUrl(), invocation);
            int timeout = calculateTimeout(invocation, methodName);
            if (isOneway) {
                boolean isSent = getUrl().getMethodParameter(methodName, Constants.SENT_KEY, false);
                currentClient.send(inv, isSent);
                return AsyncRpcResult.newDefaultAsyncResult(invocation);
            } else {
                ExecutorService executor = getCallbackExecutor(getUrl(), inv);
                CompletableFuture<AppResponse> appResponseFuture =
                        currentClient.request(inv, timeout, executor).thenApply(obj -> (AppResponse) obj);
                // save for 2.6.x compatibility, for example, TraceFilter in Zipkin uses com.alibaba.xxx.FutureAdapter
                FutureContext.getContext().setCompatibleFuture(appResponseFuture);
                AsyncRpcResult result = new AsyncRpcResult(appResponseFuture, inv);
                result.setExecutor(executor);
                return result;
            }
        } catch (TimeoutException e) {
            throw new RpcException(RpcException.TIMEOUT_EXCEPTION, "Invoke remote method timeout. method: " + invocation.getMethodName() + ", provider: " + getUrl() + ", cause: " + e.getMessage(), e);
        } catch (RemotingException e) {
            throw new RpcException(RpcException.NETWORK_EXCEPTION, "Failed to invoke remote method: " + invocation.getMethodName() + ", provider: " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }

    private int calculateTimeout(Invocation invocation, String methodName) {
        Object countdown = RpcContext.getContext().get(TIME_COUNTDOWN_KEY);
        int timeout;
        if (countdown == null) {
            timeout = (int) getTimeout(getUrl(), methodName, RpcContext.getContext(), DEFAULT_TIMEOUT);
            if (getUrl().getParameter(ENABLE_TIMEOUT_COUNTDOWN_KEY, false)) {
                invocation.setObjectAttachment(TIMEOUT_ATTACHMENT_KEY, timeout); // pass timeout to remote server
            }
        } else {
            TimeoutCountDown timeoutCountDown = (TimeoutCountDown) countdown;
            timeout = (int) timeoutCountDown.timeRemaining(TimeUnit.MILLISECONDS);
            invocation.setObjectAttachment(TIMEOUT_ATTACHMENT_KEY, timeout);// pass timeout to remote server
        }
        return timeout;
    }

    private long getTimeout(URL url, String methodName, RpcContext context, long defaultTimeout) {
        long timeout = defaultTimeout;
        try {
            // 默认读取动态timeout耗时
            Object dynamicTimeout = context.getObjectAttachment(DYNAMIC_TIMEOUT_KEY);
            Object genericTimeout = context.getObjectAttachment(TIMEOUT_KEY);
            if (dynamicTimeout != null) {
                timeout = convertToNumber(dynamicTimeout);
                // 用完马上丢弃,避免污染透传
                context.removeAttachment(DYNAMIC_TIMEOUT_KEY);
            } else if (genericTimeout != null) {
                timeout = convertToNumber(genericTimeout);
            } else if (url != null) {
                timeout = url.getMethodPositiveParameter(methodName, TIMEOUT_KEY, defaultTimeout);
            }
            return timeout;
        } catch (Exception e) {
            LOGGER.error("getTimeout error", e);
            return defaultTimeout;
        }
    }

    private static long convertToNumber(Object obj) {
        long timeout = 0;
        try {
            if (obj instanceof String) {
                timeout = Long.parseLong((String) obj);
            } else if (obj instanceof Number) {
                timeout = ((Number) obj).longValue();
            } else {
                timeout = Long.parseLong(obj.toString());
            }
        } catch (Exception e) {
            LOGGER.error("");
        }
        return timeout;
    }
}
