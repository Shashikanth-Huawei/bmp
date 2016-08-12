/*
 * Copyright 2015-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.bmp.controller;


import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.onlab.packet.Ip4Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Class for handling the BMP peer sessions.
 * There is one instance per each BMP peer session.
 */
public class BmpSession extends SimpleChannelHandler {
    private static final Logger log =
        LoggerFactory.getLogger(BmpSession.class);

    private final BmpSessionManager bmpSessionManager;

    // Local flag to indicate the session is closed.
    // It is used to avoid the Netty's asynchronous closing of a channel.
    private boolean isClosed = false;

    // BMP session info: local and remote
    private final BmpSessionInfo localInfo;     // BMP session local info
    private final BmpSessionInfo remoteInfo;    // BMP session remote info

    /**
     * Constructor for a given BMP session manager.
     *
     * @param bmpSessionManager the BMP session manager to use
     */
    BmpSession(BmpSessionManager bmpSessionManager) {
        this.bmpSessionManager = bmpSessionManager;
        this.localInfo = new BmpSessionInfo();
        this.remoteInfo = new BmpSessionInfo();
    }

    /**
     * Gets the BMP session manager.
     *
     * @return the BMP session manager
     */
    BmpSessionManager getBmpSessionManager() {
        return bmpSessionManager;
    }

    /**
     * Gets the BMP session local information.
     *
     * @return the BMP session local information.
     */
    public BmpSessionInfo localInfo() {
        return localInfo;
    }

    /**
     * Gets the BMP session remote information.
     *
     * @return the BMP session remote information.
     */
    public BmpSessionInfo remoteInfo() {
        return remoteInfo;
    }

    /**
     * Tests whether the session is closed.
     * <p>
     * NOTE: We use this method to avoid the Netty's asynchronous closing
     * of a channel.
     * </p>
     * @return true if the session is closed
     */
    boolean isClosed() {
        return isClosed;
    }

    /**
     * Closes the session.
     *
     * @param ctx the Channel handler context
     */
    void closeSession(ChannelHandlerContext ctx) {
        closeChannel(ctx);
    }

    /**
     * Closes the netty channel.
     *
     * @param ctx the channel handler context
     */
    void closeChannel(ChannelHandlerContext ctx) {
        isClosed = true;
        ctx.getChannel().close();
    }

    @Override
    public void channelOpen(ChannelHandlerContext ctx,
                            ChannelStateEvent channelEvent) {
        bmpSessionManager.addSessionChannel(channelEvent.getChannel());
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx,
                              ChannelStateEvent channelEvent) {
        bmpSessionManager.removeSessionChannel(channelEvent.getChannel());
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx,
                                 ChannelStateEvent channelEvent) {
        localInfo.setAddress(ctx.getChannel().getLocalAddress());
        remoteInfo.setAddress(ctx.getChannel().getRemoteAddress());

        // Assign the local and remote IPv4 addresses
        InetAddress inetAddr;
        if (localInfo.address() instanceof InetSocketAddress) {
            inetAddr = ((InetSocketAddress) localInfo.address()).getAddress();
            localInfo.setIp4Address(Ip4Address.valueOf(inetAddr.getAddress()));
        }
        if (remoteInfo.address() instanceof InetSocketAddress) {
            inetAddr = ((InetSocketAddress) remoteInfo.address()).getAddress();
            remoteInfo.setIp4Address(Ip4Address.valueOf(inetAddr.getAddress()));
        }

        log.debug("BMP session connected from {} on {}",
                  remoteInfo.address(), localInfo.address());
        if (!bmpSessionManager.peerConnected(this)) {
            log.debug("Cannot setup BMP Session connection from {}. Closing...",
                      remoteInfo.address());
            ctx.getChannel().close();
        }
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx,
                                    ChannelStateEvent channelEvent) {
        log.debug("BMP session disconnected from {} on {}",
                  ctx.getChannel().getRemoteAddress(),
                  ctx.getChannel().getLocalAddress());
        processChannelDisconnected();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        log.debug("BMP session exception caught from {} on {}: {}",
                  ctx.getChannel().getRemoteAddress(),
                  ctx.getChannel().getLocalAddress(),
                  e);
        log.debug("Exception:", e.getCause());
        processChannelDisconnected();
    }

    /**
     * Processes the channel being disconnected.
     */
    private void processChannelDisconnected() {
        bmpSessionManager.peerDisconnected(this);
    }
}
