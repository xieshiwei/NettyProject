package com.netty.dubborpc.provider;

import com.netty.dubborpc.netty.NettyServer;

/**
 * @ClassName ServerBootstrap
 * 启动一个服务服务提供者
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/24 19:49
 **/
public class ServerBootstrap {

    public static void main(String[] args) {

        NettyServer.startServer("127.0.0.1", 7002);
    }
}
