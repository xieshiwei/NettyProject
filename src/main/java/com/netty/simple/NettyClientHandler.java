package com.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @ClassName NettyClientHandler
 *
 * 1 继承 ChannelInboundHandlerAdapter 入站适配器对象
 * 2 重写 channelActive 在Channel注册EventLoop、绑定SocketAddress和连接ChannelFuture的时候触发
 * 3 重写 channelRead 监听到读操作时触发
 * 4 重写 exceptionCaught 异常时触发
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/19 19:30
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 在Channel注册EventLoop、绑定SocketAddress和连接ChannelFuture的时候
     * 都有可能会触发ChannelInboundHandler的channelActive方法的调用。
     * 当通道就绪就会触发该方法。
     *
     * @param ctx 上下文对象，含有管道pipeline，通道channel，地址
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client" + ctx);
        //将数据写入到ChannelPipeline中
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server:喵喵", CharsetUtil.UTF_8));
    }

    /**
     * 当通道有读取事件时，会触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }

    /**
     * Netty 异常机制
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
