package com.netty.dubborpc.provider;

import com.netty.dubborpc.publicinterface.HelloService;

/**
 * @ClassName HelloServiceImpl
 * 对HelloService的实现类
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/24 19:45
 **/
public class HelloServiceImpl implements HelloService {

    private int count = 0;

    /**
     * 根据msg返回不同结果
     *
     * @param msg
     * @return
     */
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        if (msg != null) {
            return "我已经收到了你的消息【" + msg + "] 第" + ++count + "次";
        } else {
            return "我已经收到了你的消息";
        }
    }
}
