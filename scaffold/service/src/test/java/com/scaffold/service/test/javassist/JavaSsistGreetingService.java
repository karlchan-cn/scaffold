package com.scaffold.service.test.javassist;

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
    private static final GreetingServiceImpl createGreetingService() throws NotFoundException,
            CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass gc = pool.get("com.scaffold.service.test.conf.TestApplicationConfig");
        gc.setSuperclass(pool.get("com.scaffold.service.test.javassist.JavaSsistGreetingService"));
        gc.writeFile();
        return null;
    }

    public static void main(String[] args) throws IOException, CannotCompileException, NotFoundException {
        createGreetingService();
    }
}
