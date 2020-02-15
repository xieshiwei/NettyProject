package com.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName NettyClient
 * 通过 Netty 启动客户端,连接服务器
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/18 21:43
 **/
public class NettyClient {


    /**
     * 步骤：
     * 1 创建 EventLoopGroup 线程组
     * 2 创建客户端启动对象 Bootstrap
     * 3 对启动对象进行配置
     * 3.1 group 将线程组放入启动对象
     * 3.2 channel 通过反射来设置通道的实现类
     * 3.3 handler 对通道进行初始化，重写 initChannel 方法，创建管道，添加处理器到管道中
     * 4 创建 ChannelFuture 启动客户端启动器，设置IP，端口号，进行连接
     * 5 关闭通道进行监听
     * 6 线程组优雅关闭
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //客户端需要一个事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        //创建客户端启动对象
        Bootstrap bootstrap = new Bootstrap();

        try {
            //对客户端启动项进行设置
            //设置线程
            bootstrap.group(eventExecutors)
                    //设置客户端通道的实现类（反射）
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //加入自己的处理器
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端 ok....");

            //启动客户端去连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();

        } finally {
            eventExecutors.shutdownGracefully();
        }

    }
}
