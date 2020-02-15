package com.netty.test.provider;

import com.netty.test.publicinterface.HelloService;

/**
 * @ClassName HelloServerImpl
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/27 13:42
 **/
public class HelloServerImpl implements HelloService {

    /**
     * 计数器
     */
    private int count = 0;

    /**
     * 接口实现
     * @param msg
     * @return
     */
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        if (msg == null || "".equals(msg)) {
            return "我已经收到了你的消息";
        } else {
            return "我已经收到了你的消息【" + msg + "] 第" + ++count + "次";
        }
    }
}
