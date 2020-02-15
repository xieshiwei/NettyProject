package com.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName MappedByteBufferTest
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/14 20:27
 **/
public class MappedByteBufferTest {

    /**
     * MappedByteBuffer 可以让文件直接在内存（堆外内存）修改，操作系统不需要拷贝一次
     * RandomAccessFile 可以修改文件的任意位置上的数据
     * @param args
     */
    public static void main(String[] args) throws IOException {

        //可以自由访问文件的任意位置，所以如果我们希望只访问文件的部分内容，那就可以使用RandomAccessFile类。
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\GitHub\\JavaMS\\Netty\\test.txt", "rw");
        //获取管道
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数1：使用的督学模式
         * 参数2：可以直接修改的起始位置
         * 参数3：是映射到内存的大小，test.txt的多少字节映射到内存
         * 这里可以直接修改的范围就是0~4
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'z');
        map.put(3, (byte) 'b');
        channel.close();
        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
