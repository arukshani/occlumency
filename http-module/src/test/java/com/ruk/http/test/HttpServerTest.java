package com.ruk.http.test;

import com.ruk.server.bootstrap.HttpServer;
import com.ruk.server.handler.inbound.EchoServerInboundHandler;
import com.ruk.server.initializer.HttpServerInitializer;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HttpServerTest {
    protected static final Logger LOG = LoggerFactory.getLogger(HttpServerTest.class);

    @Test
    public void startServer() {
        try {
            HttpServer httpServer = new HttpServer(9090);
            httpServer.setChannelInitializer(new HttpServerInitializer(new EchoServerInboundHandler()));
            ChannelFuture channelFuture = httpServer.start();
            Assert.assertNotNull(channelFuture, "Returned channel future after server startup is null.");
            httpServer.stop();
        } catch (InterruptedException e) {
            LOG.error("Error occured in test 'startServer", e);
        }
    }
}
