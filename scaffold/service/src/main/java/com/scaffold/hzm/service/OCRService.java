package com.scaffold.hzm.service;


public interface OCRService {
    /**
     * 读取验证码
     *
     * @param path
     * @param file
     * @return
     */
    String ocrFromImage(String path, String file);
}
