package cn.newworld.handler;

import java.nio.channels.SocketChannel;

public interface RequestHandler {
    void handlerRequestMessage(String requestMessage, SocketChannel socketChannel);
}
