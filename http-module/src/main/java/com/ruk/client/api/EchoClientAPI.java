package com.ruk.client.api;

import com.ruk.client.bootstrap.HttpClient;
import com.ruk.client.handler.inbound.EchoClientInboundHandler;
import com.ruk.client.initializer.HttpClientInitializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoClientAPI {
    protected static final Logger LOG = LoggerFactory.getLogger(EchoClientAPI.class);

    private HttpClient httpClient;
    private ChannelFuture channelFuture;

    public void connectToServer(String host, int port) throws Exception {
        httpClient = new HttpClient(host, port);
        httpClient.setClientInitializer(new HttpClientInitializer(new EchoClientInboundHandler()));
        channelFuture = httpClient.connectToServer();
    }

    public void writeToServer(String message) {
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                LOG.info(" Connected to server : " + channelFuture.channel().remoteAddress());
                LOG.info(" Client bound to  : " + channelFuture.channel().localAddress());
                channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
            }
        });
    }

    public void destroyConnection() {
        httpClient.destroyConnection();
    }
}
