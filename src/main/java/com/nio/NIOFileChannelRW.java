package com.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NIOFileChannelRW
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/14 16:06
 **/
public class NIOFileChannelRW {

    public static void main(String[] args) throws IOException {

        //读
        FileInputStream fileInputStream = new FileInputStream("D:\\GitHub\\JavaMS\\Netty\\test.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        //写
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\GitHub\\JavaMS\\Netty\\2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {

            //清空,对buffer做初始化
            byteBuffer.clear();
            //将fileChannel01的数据读入到Buffer中
            int read = fileChannel01.read(byteBuffer);
            if (read == -1) {
                break;
            }
            //将buffer 中的数据写入到 writeChannel02
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }

        //关闭通道
        fileChannel01.close();
        fileChannel02.close();

        //关闭流
        fileInputStream.close();
        fileOutputStream.close();

    }
}
