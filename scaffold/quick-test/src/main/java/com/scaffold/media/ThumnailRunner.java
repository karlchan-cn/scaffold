package com.scaffold.media;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Karl on 2021/9/26
 **/
public class ThumnailRunner {
    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("d://input.jpg"))
                .size(358, 441)
                .outputFormat("JPEG")
                .outputQuality(0.80)
                .toOutputStream(new FileOutputStream(new File("d://output.jpg")));
    }
}
