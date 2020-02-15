package com.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

import java.util.concurrent.TimeUnit;
import java.util.logging.SocketHandler;

/**
 * @ClassName MyServer
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/23 19:45
 **/
public class MyServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            /**
             * LoggingHandler由Netty提供的日志处理器
             * 在bossGroup中添加日志处理器，会把bossGroup相关信息输出
             * LogLevel是日志的级别
             */
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));

            //绑定workerGroup处理器，对ChannelInitializer进行初始化
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                /**
                 * 对 workerGroup 进行初始化设置
                 * @param ch
                 * @throws Exception
                 */
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    /**  netty 提供的 IdleStateHandler
                     * 1. IdleStateHandler 是netty 提供的处理空闲状态的处理器
                     * 2. long readerIdleTime : 表示多长时间没有读, 就会发送一个心跳检测包检测是否连接
                     * 3. long writerIdleTime : 表示多长时间没有写, 就会发送一个心跳检测包检测是否连接
                     * 4. long allIdleTime : 表示多长时间没有读写, 就会发送一个心跳检测包检测是否连接
                     * 5. 文档说明:在一段时间内读取，写入或读写同时执行的操作没有执行的时候，触发IdleStateEvent
                     * 6. 当 IdleStateEvent 触发后 , 就会传递给管道 的下一个handler去处理
                     * 通过调用(触发)下一个handler 的 userEventTiggered ,
                     * 在该方法中去处理 IdleStateEvent(读空闲，写空闲，读写空闲)
                     */
                    pipeline.addLast(new IdleStateHandler(2, 5, 10, TimeUnit.SECONDS));
                    pipeline.addLast(new MyServerHandler());
                }
            });

            //启动服务器
            ChannelFuture channelFuture = bootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
