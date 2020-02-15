package com.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @ClassName NettyServerHandler
 * 注意：
 * 1.自定义的Handler 需要继承netty规定好的一个HandlerAdapter
 * 2.这时我们自定义的Handler，才能有称为Handler
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/17 16:00
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx 上下文对象，含有管道pipeline，通道channel，地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        System.out.println("channelRead server ctx=" + ctx);

        ByteBuf buf = (ByteBuf) msg;
        System.out.println("channelRead 客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("channelRead 客户端地址：" + ctx.channel().remoteAddress());

    }

    /**
     * 数据读取完毕回调
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("channelReadComplete hello 客户端~", CharsetUtil.UTF_8));
    }

    /**
     * 异常处理，一般是需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
