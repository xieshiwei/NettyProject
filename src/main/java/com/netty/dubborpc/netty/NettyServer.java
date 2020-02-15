package com.netty.dubborpc.netty;

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
 * @Date 2019/12/24 19:51
 **/
public class NettyServer {


    public static void startServer(String hostname, int port) {
        startServer0(hostname, port);
    }


    /**
     * 编写一个方法，完成对nettyServer的初始化和启动
     * startServer+0是netty的方法
     */
    private static void startServer0(String hostname, int port) {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * channel进行初始化
                         * @param ch
                         * @throws Exception
                         */
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            //业务处理器
                            pipeline.addLast(new NettyServerHandler());

                        }
                    });
            ChannelFuture future = serverBootstrap.bind(hostname, port).sync();
            System.out.println("服务提供方开始提供服务");
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
