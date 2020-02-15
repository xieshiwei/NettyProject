package com.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NIOClient
 * @Author 萌琪琪爸爸
 * @Description //
 * 1 创建客户端socket
 * 2 设置为非阻塞
 * 3 读取服务端的IP和端口号
 * 4 连接服务器
 * 5 连接成功，发送数据，将字符串中的数据以字节的方式放到缓冲中
 * 6 发送数据，将buffer数据写入channel
 * @Date 2020/1/16 14:14
 **/
public class NIOClient {
    public static void main(String[] args) throws Exception {

        //创建客户端socket
        SocketChannel socketChannel = SocketChannel.open();

        //设置为非阻塞
        socketChannel.configureBlocking(false);

        //提供服务端的IP和端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作..");
            }
        }

        //连接成功，发送数据
        String msg = "hello I'm mengqiqi";
        //将字符串以字节的方式放到缓冲中
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        //发送数据，将buffer数据写入channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
