package com.netty.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @ClassName NettyServer
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/27 13:54
 **/
public class NettyServer {

    /**
     * netty服务端启动
     *
     * @param hostName
     * @param port
     */
    private static void startServer0(String hostName, int port) {

        //启动两个线程组
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boosGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * 通道初始化
                         * @param ch
                         * @throws Exception
                         */
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = server.bind(hostName, port).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
