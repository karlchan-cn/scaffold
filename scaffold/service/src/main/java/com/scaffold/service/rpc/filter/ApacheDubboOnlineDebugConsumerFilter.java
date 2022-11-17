package com.scaffold.service.rpc.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * 根据trace信息，进行online debug开关的切换
 */
@Activate(group = {CommonConstants.CONSUMER}, order = 100)
@Slf4j
public class ApacheDubboOnlineDebugConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //boolean openOnlineDebug = LogOnlineDebug.openOnlineDebugDependOnTrace();
        try {
            return invoker.invoke(invocation);
        } finally {
            log.info("ApacheDubboOnlineDebugFilter finally");
//            if (openOnlineDebug) {
//                LogOnlineDebug.closeOnlineDebugAndTraceId();
//            }
        }
    }
}
