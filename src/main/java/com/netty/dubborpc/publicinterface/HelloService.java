package com.netty.dubborpc.publicinterface;

/**
 * @ClassName HelloService
 * 这个借口是服务提供和消费方都需要的
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/24 19:43
 **/
public interface HelloService {

    String hello(String msg);
}
