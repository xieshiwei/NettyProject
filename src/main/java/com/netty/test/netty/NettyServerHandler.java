package com.netty.test.netty;

import com.netty.test.provider.HelloServerImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName NettyServerHandler
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/27 14:08
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取事件触发，调用provider实现接口
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("触发channelRead");
        String result = new HelloServerImpl().hello(msg.toString());
        ctx.writeAndFlush(result);
    }

    /**
     * 异常
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
