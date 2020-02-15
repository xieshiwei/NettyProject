package com.netty.dubborpc.netty;

import com.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName NettyServerHandler
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/24 20:14
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * netty读触发事件
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器API时，我们需要定义一个协议，所有字符串开头都以"HelloService#hello#"
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result =
                    new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            /**
             * writeAndFlush会做两件事情
             * 1 invokeWrite0(msg, promise)；将消息放入输出缓冲区中（ChannelOutboundBuffer）
             * 2 invokeFlush0(); 将输出缓冲区中的数据通过socket发送到网络中
             */
            ctx.writeAndFlush(result);
        }
    }

    /**
     * 异常关闭通道
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
