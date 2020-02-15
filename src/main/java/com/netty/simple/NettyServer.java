package com.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName NettyServer
 * 通过Netty启动服务端
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/17 15:49
 **/
public class NettyServer {


    /**
     * 步骤：
     * 1 创建两个 EventLoopGroup 线程组 bossGroup，workerGroup
     * 2 创建 ServerBootstrap 服务端启动对象
     * 3 对启动对象进行配置
     * 3.1 group 将线程组放入启动对象
     * 3.2 channel 通过反射来设置通道的实现类 NioServerSocketChannel
     * 3.3 option 为 bossGroup 设置连接数
     * 3.4 childOption 为 workerGroup 设置通道活动的连接状态
     * 3.5 childHandler 为 workerGroup 的 channel 进行初始化
     * 重写 initChannel 方法，创建管道，添加处理器到管道中
     * 4 创建一个 ChannelFuture 对象，绑定端口号，启动 ServerBootstrap 服务端启动对象
     * 5 对关闭通道进行监听
     * 6 boosgroup workergroup 线程组优雅关闭
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        /**
         * NioEventLoopGroup，包含子线程的个数
         * 默认是当前CPU核数*2，例如4核CPU NioEventLoopGroup默认会开启8个线程
         * 源码：NettyRuntime.availableProcessors() * 2
         */

        //处理链接请求的线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //业务处理的线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建服务端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            //服务端启动对象进行参数设置
            //设置两个线程组
            bootstrap.group(bossGroup, workerGroup)
                    //使用Nio作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //Channel初始化的对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * 给workerGroup的EventLoop对应的管道设置处理器
                         * @param ch
                         * @throws Exception
                         */
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("....服务器 is ready .....");

            //绑定一个端口并且同步，生成一个ChannelFuture对象,启动服务器（绑定端口）
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();

        } finally {
            //关闭boostgroup线程组
            bossGroup.shutdownGracefully();
            //关闭workergroup线程组
            workerGroup.shutdownGracefully();
        }

    }
}
