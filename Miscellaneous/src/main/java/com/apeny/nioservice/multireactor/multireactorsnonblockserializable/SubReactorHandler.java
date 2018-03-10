package com.apeny.nioservice.multireactor.multireactorsnonblockserializable;

import com.apeny.nioservice.multireactor.multireactorsnonblockserializable.state.HandlerState;
import com.apeny.nioservice.multireactor.multireactorsnonblockserializable.state.ReadState;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 根据当前处理者状态 调起读或写操作
 * 经验：线程中断和线程推出不是相同意义，线程中断，线程依然活着；线程退出，线程就结束生命周期。
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
            selectionKey.cancel();
            socketChannel.close();
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}