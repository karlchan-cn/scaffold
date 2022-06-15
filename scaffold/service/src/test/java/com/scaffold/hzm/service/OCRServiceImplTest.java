package com.scaffold.hzm.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class OCRServiceImplTest {
    OCRService ocrService = new OCRServiceImpl();

    @Test
    public void ocrFromImage() {
        assertNotNull(ocrService.ocrFromImage("D:\\dev\\task_list\\2022\\202206\\HZMBusTicket", "captcha.jpg"));
    }
}