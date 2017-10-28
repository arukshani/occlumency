package com.ruk.client.handler.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoClientInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
    protected static final Logger LOG = LoggerFactory.getLogger(EchoClientInboundHandler.class);

    /**
     * As soon as the incoming channel gets active we send another message to server.
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer(" All good! ", CharsetUtil.UTF_8));
    }

    /**
     * Read data coming from server.
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        LOG.info("Client received: " + msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("Exception in EchoClientInboundHandler. ", cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Channel got inactive in EchoClientInboundHandler");
        }
        ctx.close();
    }
}
