package com.scaffold.hzm.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.io.File;

//@Slf4j
public class OCRServiceImpl implements OCRService {
    public static void main(String[] args) {
        new OCRServiceImpl().ocrFromImage("D:\\dev\\task_list\\2022\\202206\\HZMBusTicket", "captcha.jpg");
    }
    @Override
    public String ocrFromImage(String path, String file) {
        ImageIO.scanForPlugins();
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(path);
        try {
            String ocrResult = tesseract.doOCR(new File("..."));
            System.out.println(ocrResult);
            return ocrResult;
        } catch (TesseractException e) {
            e.printStackTrace();
            //log.error("error", e);
            return null;
        }
    }
}
