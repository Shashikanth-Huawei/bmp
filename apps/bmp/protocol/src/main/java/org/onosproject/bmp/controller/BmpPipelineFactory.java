/*
 * Copyright 2016-present Open Networking Laboratory
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

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.ExternalResourceReleasable;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

/**
 * Creates a ChannelPipeline for a server-side BMP channel.
 */
public class BmpPipelineFactory
    implements ChannelPipelineFactory, ExternalResourceReleasable {

    static final Timer TIMER = new HashedWheelTimer();
    protected ReadTimeoutHandler readTimeoutHandler;
    private boolean isBmpServ;
    private BmpSessionManager sessionManager;

    /**
     * Constructor to initialize the values.
     *
     * @param sessionManager session manager
     * @param isBmpServ if it is a server or remote peer
     */
    public BmpPipelineFactory(BmpSessionManager sessionManager,boolean isBmpServ) {
        super();
        this.sessionManager = sessionManager;
        this.isBmpServ = isBmpServ;
        /* hold time */
        //this.readTimeoutHandler = new ReadTimeoutHandler(TIMER, bgpController.getConfig().getHoldTime());
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        BmpSession handler = new BmpSession(sessionManager);

        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("BmpMessageDecoder", new BmpMessageDecoder());
        pipeline.addLast("BmpMessageencoder", new BmpMessageEncoder());
        //pipeline.addLast("holdTime", readTimeoutHandler);
        if (isBgpServ) {
            pipeline.addLast("PassiveHandler", handler);
        } else {
            pipeline.addLast("ActiveHandler", handler);
        }

        return pipeline;
    }

    @Override
    public void releaseExternalResources() {
        TIMER.stop();
    }
}
