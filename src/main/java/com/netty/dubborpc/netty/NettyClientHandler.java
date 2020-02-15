package com.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @ClassName NettyClientHandler
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/24 20:24
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    //上下文
    private ChannelHandlerContext context;
    //返回结果
    private String result;
    //传入的参数
    private String para;


    /**
     * 与服务器的连接创建后，被调用
     * channelActive 只会被建立一次，因为通道已经形成了
     * 第一个被调用的方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive 被调用");
        context = ctx;
    }

    /**
     * 第二个被调用的方法
     *
     * @param para
     */
    public void setPara(String para) {
        System.out.println("setPara 被调用");
        this.para = para;
    }

    /**
     * 被代理对象调用，发送数据给服务器，发送后wait，等待被唤醒
     * 第三个被调用的（等待）
     * 第五个被调用的（等待结束，继续执行）
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call1 被调用");
        context.writeAndFlush(para);
        //等待ChannelRead 获取到服务器结果后才会唤醒
        wait();
        System.out.println("call2 被调用");
        return result;
    }

    /**
     * 收到服务器的数据后会被调用
     * 第四个被调用的
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead 被调用");
        result = msg.toString();
        //唤醒等待的线程
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught 被调用");
        ctx.close();
    }


}
