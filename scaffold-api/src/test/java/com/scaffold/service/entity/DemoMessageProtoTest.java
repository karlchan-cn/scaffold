package com.scaffold.service.entity;

import org.junit.Test;

import java.io.*;

/**
 * Created by Karl on 2021/12/27
 **/
public class DemoMessageProtoTest {
    /**
     * 测试message to file.
     */
    @Test
    public void testDemomessageToFile() throws IOException {
        final DemoMessage dm = DemoMessage.newBuilder().setId(150).setName("testing").build();
        try(FileOutputStream fileOutputStream = new FileOutputStream("demomessage-val.out")){
            dm.writeTo(fileOutputStream);
        }
        //DemoMessage.newBuilder().mergeFrom(new Fil);
    }

}
