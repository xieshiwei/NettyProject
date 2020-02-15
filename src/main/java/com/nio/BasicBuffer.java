package com.nio;

import javax.swing.*;
import java.nio.IntBuffer;

/**
 * @ClassName BasicBuffer
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/13 15:07
 **/
public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个长度为5，可以存放5个int的容器
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {

        }
    }


}
