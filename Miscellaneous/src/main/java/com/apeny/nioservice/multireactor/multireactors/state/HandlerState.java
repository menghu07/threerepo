package com.apeny.nioservice.multireactor.multireactors.state;

import com.apeny.nioservice.multireactor.multireactors.SubReactorHandler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 状态模式实现Handler,状态界面
 * Created by apeny on 2018/3/4.
 */
public interface HandlerState  {

    /**
     * 设置Handler的State
     * @param handler
     */
    void setState(SubReactorHandler handler);

    /**
     * 处理IO事件
     * @param handler
     * @param selectionKey
     * @param socketChannel
     * @throws IOException
     */
    void handle(SubReactorHandler handler, SelectionKey selectionKey, SocketChannel socketChannel) throws IOException;
}
