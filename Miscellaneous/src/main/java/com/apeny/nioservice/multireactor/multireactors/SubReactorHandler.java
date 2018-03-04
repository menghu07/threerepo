package com.apeny.nioservice.multireactor.multireactors;

import com.apeny.nioservice.multireactor.multireactors.state.HandlerState;
import com.apeny.nioservice.multireactor.multireactors.state.ReadState;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 根据当前处理者状态 调起读或写操作
 */
public class SubReactorHandler implements Runnable {
    private final SocketChannel socketChannel;
    private SelectionKey selectionKey;

    private volatile HandlerState state = new ReadState();

    SubReactorHandler(SelectionKey selectionKey, SocketChannel socketChannel) throws IOException {
        this.selectionKey = selectionKey;
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            state.handle(this, selectionKey, socketChannel);
        } catch (Throwable e) {
            System.out.println("client exception and exit");
            closeChannel();
        }
    }

    public void setState(HandlerState state) {
        this.state = state;
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public void closeChannel() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}