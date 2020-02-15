package com.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName GroupChatClientHandler
 * @Author 萌琪琪爸爸
 * @Description //TODO
 * @Date 2019/12/23 19:29
 **/
public class GroupChatClientHandler extends SimpleChannelInboundHandler {


    /**
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String) {
            System.out.println((String) msg);
        } else {
            System.out.println("类型错误");
        }
    }
}
