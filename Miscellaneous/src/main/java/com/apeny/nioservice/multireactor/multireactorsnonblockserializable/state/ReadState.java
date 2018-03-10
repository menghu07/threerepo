package com.apeny.nioservice.multireactor.multireactorsnonblockserializable.state;

import com.apeny.nioservice.multireactor.multireactorsnonblockserializable.SubReactorHandler;
import com.apeny.nioservice.multireactor.multireactorsnonblockserializable.SystemEnvironment;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 读状态
 * Created by apeny on 2018/3/4.
 */
public class ReadState implements HandlerState {
    private volatile String rawData;

    @Override
    public final void setState(SubReactorHandler handler) {
        handler.setState(new ProcessState(rawData));
    }

    @Override
    public void handle(SubReactorHandler handler, SelectionKey selectionKey, SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int length = socketChannel.read(byteBuffer);
        if (length == -1) {
            System.out.println("Client closed, so server will close");
            handler.closeChannel();
        } else {
            String readData = new String(byteBuffer.array(), 0, length);
            if (!"".equals(readData)) {
                this.rawData = readData;
                setState(handler);
                SystemEnvironment.WORKER_POOL.execute(new WorkerThread(handler));
                System.out.println(socketChannel.socket().getRemoteSocketAddress() + ">" + readData);
            }
        }
    }

    private synchronized void process(SubReactorHandler handler) {
//        Logger.getLogger("system").log(Level.INFO, "from client: [" + rawData + "]" + ", dataLength: " + rawData.length());
        handler.setState(new WriteState(rawData));
        //READ -> WRITE
        handler.getSelectionKey().interestOps(SelectionKey.OP_WRITE);
        handler.getSelectionKey().selector().wakeup();
    }

    class WorkerThread implements Runnable {

        private final SubReactorHandler handler;

        WorkerThread(SubReactorHandler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            process(handler);
        }
    }
}
