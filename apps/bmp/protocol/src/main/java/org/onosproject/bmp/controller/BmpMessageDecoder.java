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

import java.util.LinkedList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.onosproject.bmp.bmpio.protocol.BmpMessage;
import org.onlab.util.HexDump;
import org.onosproject.bmp.bmpio.protocol.BmpFactories;
import org.onosproject.bmp.bmpio.protocol.BmpMessageReader;
import org.onosproject.bmp.bmpio.types.BmpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Decode an BMP message from a Channel, for use in a netty pipeline.
 */
public class BmpMessageDecoder extends FrameDecoder {

    protected static final Logger log = LoggerFactory.getLogger(BmpMessageDecoder.class);

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        log.debug("MESSAGE IS RECEIVED.");
        if (!channel.isConnected()) {
            log.info("Channel is not connected.");
            return null;
        }

        HexDump.dump(buffer);

        BmpMessageReader<BmpMessage> reader = BmpFactories.getGenericReader();
        List<BmpMessage> msgList = (List<BmpMessage>) ctx.getAttachment();

        if (msgList == null) {
            msgList = new LinkedList<>();
        }

        try {
            while (buffer.readableBytes() > 0) {
                buffer.markReaderIndex();
                BmpHeader bmpHeader = new BmpHeader();
                BmpMessage message = reader.readFrom(buffer, bmpHeader);
                msgList.add(message);
            }
            ctx.setAttachment(null);
            return msgList;
        } catch (Exception e) {
            log.debug("BMP protocol message decode error");
            buffer.resetReaderIndex();
            buffer.discardReadBytes();
            ctx.setAttachment(msgList);
        }
        return null;
    }
}
