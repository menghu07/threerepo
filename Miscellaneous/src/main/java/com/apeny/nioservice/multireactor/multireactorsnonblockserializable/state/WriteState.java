package com.apeny.nioservice.multireactor.multireactorsnonblockserializable.state;

import com.apeny.nioservice.multireactor.multireactorsnonblockserializable.SubReactorHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 写状态
 * Created by apeny on 2018/3/4.
 */
public class WriteState implements HandlerState {
    /**
     * 客户端输入数据
     */
    private String rawData;

    WriteState(String rawData) {
        this.rawData = rawData;
    }

    @Override
    public void setState(SubReactorHandler handler) {
        handler.setState(new ReadState());
    }

    @Override
    public void handle(SubReactorHandler handler, SelectionKey selectionKey, SocketChannel socketChannel) throws IOException {
        String toClient = "hello client, i had receive your data: " + rawData;
        socketChannel.write(ByteBuffer.wrap(toClient.getBytes()));
        setState(handler);
        //WRITE -> READ
        selectionKey.interestOps(SelectionKey.OP_READ);
    }
}
