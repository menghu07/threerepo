package com.apeny.nioservice.multireactor.multireactors.state;

import com.apeny.nioservice.multireactor.multireactors.SubReactorHandler;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 处理状态
 * Created by apeny on 2018/3/4.
 */
public class ProcessState implements HandlerState {
    private String rawData;

    ProcessState(String rawData) {
        this.rawData = rawData;
    }

    @Override
    public void setState(SubReactorHandler handler) {
        handler.setState(new WriteState(rawData));
    }

    @Override
    public void handle(SubReactorHandler handler, SelectionKey selectionKey, SocketChannel socketChannel) {
        // do nothing
    }
}
