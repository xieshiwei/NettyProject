package com.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName NettyByteBuf01
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/21 16:12
 **/
public class NettyByteBuf01 {
    /**
     * ByteBuf的相关问题
     * 步骤：
     * 1 创建一个ByteBuf对象
     * 2 netty中，不需要使用flip进行反转，底层维护了readerindex和writerIndex来实现
     * 3 通过readerindex和writerindex和capacity 将buffer分成三个区域
     * 3.1 0~readindex：已经读取的区域
     * 3.2 readindex~writerindex：可读区域
     * 3.3 writerindex~capacity：可写区域
     *
     * @param args
     */
    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity=" + buffer.capacity());

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }

        System.out.println("执行完毕");
    }
}
