package com.ruk.server.handler.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for echoing back received messages as is.
 */
@ChannelHandler.Sharable
public class EchoServerInboundHandler extends ChannelInboundHandlerAdapter {
    protected static final Logger LOG = LoggerFactory.getLogger(EchoServerInboundHandler.class);

    /**
     * Receive incoming messages and writes it back to client.
     *
     * @param ctx Channel handler context
     * @param msg Incoming message
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf incomingMsg = (ByteBuf) msg;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Incoming message : " + incomingMsg.toString(CharsetUtil.UTF_8));
        }
        ctx.write(incomingMsg);
    }

    /**
     * Will get called once the last message is read by channelRead. We need to write an empty message to notify
     * client of the end of message and then close the channel.
     *
     * @param ctx Channel handler context
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Channel got inactive in EchoServerInboundHandler");
        }
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("Exception in EchoServerInboundHandler. ", cause);
        ctx.close();
    }
}
