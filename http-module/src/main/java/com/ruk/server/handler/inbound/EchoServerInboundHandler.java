package com.ruk.server.handler.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for echoing back received messages as is.
 */
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
            LOG.debug("Incoming message : " + incomingMsg.toString());
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
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
}
