package com.ruk.server.api;

import com.ruk.server.bootstrap.HttpServer;
import com.ruk.server.handler.inbound.EchoServerInboundHandler;
import com.ruk.server.initializer.HttpServerInitializer;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoHttpServerAPI {
    protected static final Logger LOG = LoggerFactory.getLogger(EchoHttpServerAPI.class);

    HttpServer httpServer;
    ChannelFuture channelFuture;

    public EchoHttpServerAPI() {

    }

    public void startServer(int port) throws InterruptedException {
        httpServer = new HttpServer(port);
        httpServer.setChannelInitializer(new HttpServerInitializer(new EchoServerInboundHandler()));
        channelFuture = httpServer.start();
    }

    public void stopServer() {
        while (true) {
            if (channelFuture.isDone()) {
                try {
                    httpServer.stop();
                } catch (InterruptedException e) {
                    LOG.error("Error occurred while stopping the server", e);
                }
                break;
            }
        }
    }
}
