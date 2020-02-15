package com.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @ClassName NIOByteBufferPutGet
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/14 20:02
 **/
public class NIOByteBufferPutGet {

    /**
     * put的顺序和set的顺序必须保持一致，否则获取元素会错位
     */
    public static void testBufferPutGet(ByteBuffer byteBuffer) {

        byteBuffer.putInt(100);
        byteBuffer.putLong(9);
        byteBuffer.putChar('谢');
        byteBuffer.putShort((short) 4);

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }

    /**
     * Buffer只读方法的测试
     *
     * @param byteBuffer
     */
    public static void testReadOnlyBuffer(ByteBuffer byteBuffer) {
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.flip();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
        while (readOnlyBuffer.hasRemaining()) {
            //Get方法会自动指针+1
            System.out.println(readOnlyBuffer.get());
        }
    }


    public static void main(String[] args) {

        //创建缓冲
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        testReadOnlyBuffer(byteBuffer);
    }
}
