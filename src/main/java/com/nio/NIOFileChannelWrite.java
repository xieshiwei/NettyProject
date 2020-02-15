package com.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.InputMismatchException;

/**
 * @ClassName NIOFileChannel
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/14 11:30
 **/
public class NIOFileChannelWrite {

    /**
     * 判断当前路径是否存在，如果不存在就创建一个文件
     *
     * @param path
     */
    public static void checkFilePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void testFileChannel() throws Exception {
        String str = "hello";
        //判断当前路径是否存在
        //checkFilePath("./test.txt");

        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\GitHub\\JavaMS\\Netty\\test.txt");

        //通过fileoutputstream获取对应的filechannel
        //这个filechannel真实类型是filechannelImp
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区bytebuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入到bytebuffer
        byteBuffer.put(str.getBytes());

        //对bytebuffer 进行flip
        byteBuffer.flip();

        //将bytebuffer数据写入到filechannel
        fileChannel.write(byteBuffer);

        //关闭通道
        fileChannel.close();

        //关闭流
        fileOutputStream.close();

    }


    public static void main(String[] args) throws Exception {
        testFileChannel();
    }
}
