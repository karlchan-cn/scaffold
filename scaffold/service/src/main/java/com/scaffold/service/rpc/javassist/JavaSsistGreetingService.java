package com.scaffold.service.rpc.javassist;

import com.scaffold.service.rpc.GreetingServiceImpl;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

/**
 * javassist测试demo.
 */
public class JavaSsistGreetingService {
    /**
     * 创建greeting service实例.
     *
     * @return javassist创建的实例.
     */
    private static final GreetingServiceImpl createGreetingService() throws Exception {
        Class.forName("com.scaffold.service.rpc.GreetingServiceImpl");
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath("D:\\dev\\source\\ads-gravity\\dev-project\\scaffold\\service\\target\\classes");
        CtClass gc = pool.get("com.scaffold.service.rpc.GreetingServiceImpl");
        gc.setSuperclass(pool.get("com.scaffold.service.rpc.javassist.JavaSsistGreetingService"));
        gc.writeFile();

        return null;
    }

    public static void main(String[] args) throws IOException, CannotCompileException, NotFoundException {
        try {
            createGreetingService();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
