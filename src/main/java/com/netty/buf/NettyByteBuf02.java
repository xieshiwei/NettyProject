package com.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @ClassName NettyByteBuf02
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/21 16:18
 **/
public class NettyByteBuf02 {

    public static void main(String[] args) {


        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world", Charset.forName("utf-8"));

        //使用相关的方法
        if (byteBuf.hasArray()) {
            byte[] context = byteBuf.array();
            //将 content 转换成字串
            System.out.println(new String(context, Charset.forName("utf-8")));
        }
    }
}
