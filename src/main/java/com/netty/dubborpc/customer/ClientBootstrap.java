package com.netty.dubborpc.customer;

import com.netty.dubborpc.netty.NettyClient;
import com.netty.dubborpc.publicinterface.HelloService;

/**
 * @ClassName ClientBootstrap
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/24 21:15
 **/
public class ClientBootstrap {

    /**
     * 协议头
     */
    public static final String PROVIDERNAME = "HelloService#hello#";


    public static void main(String[] args) throws Exception {

        //创建一个消费者
        NettyClient customer = new NettyClient();

        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, PROVIDERNAME);

        for (;; ) {
            Thread.sleep(2 * 1000);
            //通过代理对象调用服务提供者的方法(服务)
            String res = service.hello("你好 dubbo~");
            System.out.println("调用的结果 res= " + res);
        }
    }
}
