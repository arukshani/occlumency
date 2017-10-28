package com.ruk.client.bootstrap;

import com.ruk.client.initializer.HttpClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class HttpClient {
    protected static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);

    private final String host;
    private final int port;
    private EventLoopGroup eventLoopGroup;
    private HttpClientInitializer clientInitializer;
    private ChannelFuture channelFuture;

    public HttpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ChannelFuture connectToServer() throws Exception {
        eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
                .handler(clientInitializer);
        channelFuture = bootstrap.connect();
        return channelFuture;
    }

    public void destroyConnection() {
        try {
            channelFuture.channel().closeFuture();
            eventLoopGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            LOG.error("Error destroying connection to server.", e);
        }
    }

    public void setClientInitializer(HttpClientInitializer clientInitializer) {
        this.clientInitializer = clientInitializer;
    }
}
