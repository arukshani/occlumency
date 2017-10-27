package com.ruk.client.initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;

public class HttpClientInitializer extends ChannelInitializer {

    private ChannelInboundHandlerAdapter inboundHandlerAdapter;

    public HttpClientInitializer(ChannelInboundHandlerAdapter inboundHandlerAdapter) {
        this.inboundHandlerAdapter = inboundHandlerAdapter;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(inboundHandlerAdapter);
    }
}
