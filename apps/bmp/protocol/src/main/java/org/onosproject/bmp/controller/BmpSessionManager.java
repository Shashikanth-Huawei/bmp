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

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.onlab.packet.Ip4Address;


import org.onosproject.incubator.net.routing.RouteAdminService;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Dictionary;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static org.onlab.util.Tools.groupedThreads;

/**
 * BGP Session Manager class.
 */
@Component(immediate = true, enabled = true)
@Service
public class BmpSessionManager implements BmpInfoService {
    private static final Logger log =
            LoggerFactory.getLogger(BmpSessionManager.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected RouteAdminService routeService;

    boolean isShutdown = true;
    private Channel serverChannel;     // Listener for incoming BGP connections
    private ServerBootstrap serverBootstrap;
    private ChannelGroup allChannels = new DefaultChannelGroup();
    private ConcurrentMap<SocketAddress, BmpSession> bmpSessions =
            new ConcurrentHashMap<>();

    private static final int DEFAULT_BMP_PORT = 3000;
    private int bgpPort;

    @Activate
    protected void activate(ComponentContext context) {
        readComponentConfiguration(context);
        start();
        log.info("started");
    }

    @Deactivate
    protected void deactivate() {
        stop();
        log.info("stopped");
    }

    /**
     * Extracts properties from the component configuration context.
     *
     * @param context the component context
     */
    private void readComponentConfiguration(ComponentContext context) {
        Dictionary<?, ?> properties = context.getProperties();
        try {
            String strPort = (String) properties.get("bmpPort");
            if (strPort != null) {
                bgpPort = Integer.parseInt(strPort);
            } else {
                bgpPort = DEFAULT_BMP_PORT;
            }
        } catch (NumberFormatException | ClassCastException e) {
            bgpPort = DEFAULT_BMP_PORT;
        }
        log.debug("BMP port is set to {}", bgpPort);
    }

    @Modified
    public void modified(ComponentContext context) {
        // Blank @Modified method to catch modifications to the context.
        // If no @Modified method exists, it seems @Activate is called again
        // when the context is modified.
    }

    /**
     * Checks whether the BMP session manager is shutdown.
     *
     * @return true if the BMP session manager is shutdown, otherwise false
     */
    boolean isShutdown() {
        return this.isShutdown;
    }

    /**
     * Gets the BMP sessions.
     *
     * @return the BMP sessions
     */
    @Override
    public Collection<BmpSession> getBmpSessions() {
        return bmpSessions.values();
    }


    /**
     * Adds the channel for a BMP session.
     *
     * @param channel the channel to add
     */
    void addSessionChannel(Channel channel) {
        allChannels.add(channel);
    }

    /**
     * Removes the channel for a BMP session.
     *
     * @param channel the channel to remove
     */
    void removeSessionChannel(Channel channel) {
        allChannels.remove(channel);
    }

    /**
     * Processes the connection from a BMP peer.
     *
     * @param bmpSession the BMP session for the peer
     * @return true if the connection can be established, otherwise false
     */
    boolean peerConnected(BmpSession bmpSession) {

        // Test whether there is already a session from the same remote
        if (bmpSessions.get(bmpSession.remoteInfo().address()) != null) {
            return false;               // Duplicate BGP session
        }
        bmpSessions.put(bmpSession.remoteInfo().address(), bmpSession);

        if (bmpSession.localInfo().address() instanceof InetSocketAddress) {
            InetAddress inetAddr =
                ((InetSocketAddress) bmpSession.localInfo().address()).getAddress();
            Ip4Address ip4Address = Ip4Address.valueOf(inetAddr.getAddress());
        }
        return true;
    }

    /**
     * Processes the disconnection from a BMP peer.
     *
     * @param BmpSession the BMP session for the peer
     */
    void peerDisconnected(BmpSession bmpSession) {
        bmpSessions.remove(bmpSession.remoteInfo().address());
    }

    public void start() {
        log.debug("BMP Session Manager start.");
        isShutdown = false;

        ChannelFactory channelFactory = new NioServerSocketChannelFactory(
                newCachedThreadPool(groupedThreads("onos/bmp", "sm-boss-%d", log)),
                newCachedThreadPool(groupedThreads("onos/bmp", "sm-worker-%d", log)));
        ChannelPipelineFactory pipelineFactory = () -> {
            // Allocate a new session per connection
            BmpSession bmpSessionHandler =
                    new BmpSession(BmpSessionManager.this);
            BmpFrameDecoder bmpFrameDecoder =
                    new BmpFrameDecoder(bmpSessionHandler);

            // Setup the processing pipeline
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("BmpFrameDecoder", bmpFrameDecoder);
            pipeline.addLast("BmpSession", bmpSessionHandler);
            return pipeline;
        };
        InetSocketAddress listenAddress =
                new InetSocketAddress(bgpPort);

        serverBootstrap = new ServerBootstrap(channelFactory);
        // serverBootstrap.setOptions("reuseAddr", true);
        serverBootstrap.setOption("child.keepAlive", true);
        serverBootstrap.setOption("child.tcpNoDelay", true);
        serverBootstrap.setPipelineFactory(pipelineFactory);
        try {
            serverChannel = serverBootstrap.bind(listenAddress);
            allChannels.add(serverChannel);
        } catch (ChannelException e) {
            log.debug("Exception binding to BMP port {}: ",
                      listenAddress.getPort(), e);
        }
    }

    public void stop() {
        isShutdown = true;
        allChannels.close().awaitUninterruptibly();
        serverBootstrap.releaseExternalResources();
    }
}
