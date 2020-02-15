package com.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @ClassName NIOCopyPicture
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/14 19:52
 **/
public class NIOCopyPicture {

    public static void main(String[] args) throws IOException {

        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("D:\\GitHub\\JavaMS\\Netty\\gd.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\GitHub\\JavaMS\\Netty\\1.jpg");
        //获取各个流对应的管道
        FileChannel sourceh = fileInputStream.getChannel();
        FileChannel destch = fileOutputStream.getChannel();

        //使用transferFrom完成拷贝
        destch.transferFrom(sourceh, 0, sourceh.size());

        //关闭通道
        sourceh.close();
        destch.close();

        //关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
