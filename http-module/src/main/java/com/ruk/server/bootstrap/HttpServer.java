package com.ruk.server.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Http Sever class.
 */
public class HttpServer {
    protected static final Logger LOG = LoggerFactory.getLogger(HttpServer.class);

    private final int port;
    private ChannelInitializer channelInitializer;
    private EventLoopGroup eventLoopGroup;
    private ChannelFuture channelFuture;

    public HttpServer(int port) {
        this.port = port;
    }

    public ChannelFuture start() throws InterruptedException {
        eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port)).childHandler(channelInitializer);
        channelFuture = serverBootstrap.bind();
        channelFuture.sync();
        LOG.info(HttpServer.class.getName() +
                " started and listening for connections on " + channelFuture.channel().localAddress());
        return channelFuture;
    }

    public void stop() throws InterruptedException {
        LOG.debug("Shutting down server");
        channelFuture.channel().close();
        eventLoopGroup.shutdownGracefully().sync();
    }

    public void setChannelInitializer(ChannelInitializer channelInitializer) {
        this.channelInitializer = channelInitializer;
    }
}
