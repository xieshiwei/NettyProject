package com.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName 通过创建缓冲区Buffer，利用管道Channel读取字节流
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/14 14:54
 **/
public class NIOFileChannelRead {

    /**
     * 通过创建缓冲区Buffer，利用管道Channel读取字节流
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //创建文件的输入流
        File file = new File("D:\\GitHub\\JavaMS\\Netty\\test.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过fileinputstream获取对应的filechannel->实际类型filechannel
        FileChannel fileChannel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将通道的数据读入到buffer
        fileChannel.read(byteBuffer);

        //将bytebuffer的字节数据转成string
        System.out.println(new String(byteBuffer.array()));

        //关闭通道
        fileChannel.close();

        //关闭字节流
        fileInputStream.close();

    }
}
