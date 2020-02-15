package com.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NettyClient
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/24 20:53
 **/
public class NettyClient {

    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler client;
    private int count = 0;

    /**
     * 编写方法使用代理模式，获取一个对象
     * @param serivceClass
     * @param providerName
     * @return
     */
    public Object getBean(final Class<?> serivceClass, final String providerName) {
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serivceClass}, (proxy, method, args) -> {

                    System.out.println("（proxy, method, args）进入..." + (++count) + " 次");
                    /**
                     * {} 部分的代码，客户端每调用一次hello，就会进入到代码块
                     */
                    if (client == null) {
                        initClient();
                    }
                    //设置要发给服务器的信息
                    //providerName 协议头，args[0]就是客户端调用api 参数
                    client.setPara(providerName + args[0]);

                    /**
                     * 1 submit的参数必须要实现 Runnable 或者 Callable 接口
                     * 2 通过 get() 获取返回的计算结果
                     */
                    return executor.submit(client).get();
                }
        );
    }

    /**
     * 初始化客户端
     */
    private static void initClient() {
        client = new NettyClientHandler();

        //创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);

                    }
                });

        try {
            bootstrap.connect("127.0.0.1", 7001).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
