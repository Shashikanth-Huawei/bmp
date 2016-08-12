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

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.onosproject.bmp.bmpio.BmpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for handling the decoding of the BGP messages.
 */
class BmpFrameDecoder extends FrameDecoder {
    private static final Logger log =
        LoggerFactory.getLogger(BmpFrameDecoder.class);

    private final BmpSession bmpSession;

    /**
     * Constructor for a given BGP Session.
     *
     * @param BmpSession the BGP session state to use.
     */
    BmpFrameDecoder(BmpSession bmpSession) {
        this.bmpSession = bmpSession;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx,
                            Channel channel,
                            ChannelBuffer buf) throws Exception {
        //
        // NOTE: If we close the channel during the decoding, we might still
        // see some incoming messages while the channel closing is completed.
        //
        if (bmpSession.isClosed()) {
            return null;
        }

        log.trace("BMP peer: decode(): remoteAddr = {} localAddr = {} " +
                  "messageSize = {}",
                  ctx.getChannel().getRemoteAddress(),
                  ctx.getChannel().getLocalAddress(),
                  buf.readableBytes());

        // Test for minimum length of the BGP message
        if (buf.readableBytes() < BmpConstants.BMP_HEADER_LENGTH) {
            // No enough data received
            return null;
        }

        //
        // Mark the current buffer position in case we haven't received
        // the whole message.
        //
        buf.markReaderIndex();

        if (buf.readableBytes() < BmpConstants.BMP_HEADER_LENGTH) {
            log.debug("BMP RX Error: invalid header field. " +
                              "must be atleast {}", BmpConstants.BMP_HEADER_LENGTH);

            //ctx.getChannel().write(txMessage);
            //bmpSession.closeSession(ctx);
            return null;
        }

        byte version = buf.readByte();
        int msglength = buf.readInt();
        byte msgType = buf.readByte();

        if (buf.readableBytes() < msglength) {
            log.debug("BMP RX Error: invalid Length field {}. ", msglength);
            return null;
        }

        /** TBD : Per-Peer Header */

        //
        // Process the remaining of the message based on the message type
        //
        switch (msgType) {
        case BmpConstants.BMP_ROUTE_MONITORING:

            break;
        case BmpConstants.BMP_PEER_DOWN_NOTIFICATION:

            break;
        case BmpConstants.BMP_STATS_REPORTS:

            break;
        case BmpConstants.BMP_PEER_UP_NOTIFICATION:

            break;
        case BmpConstants.BMP_INITIATION:

            break;
        case BmpConstants.BMP_TERMINATION:

            break;
        case BmpConstants.BMP_ROUTE_MIRRORING:

            break;
        default:
            //
            // ERROR: Bad Message Type
            //
            // Send NOTIFICATION and close the connection
            int errorCode = BmpConstants.Notifications.MessageHeaderError.ERROR_CODE;
            int errorSubcode = BmpConstants.Notifications.MessageHeaderError.BAD_MESSAGE_TYPE;
            ChannelBuffer data = ChannelBuffers.buffer(1);
            data.writeByte(msgType);
            ChannelBuffer txMessage = null; /* =
                BmpNotification.prepareBmpNotification(errorCode, errorSubcode,
                                                       data);*/
            ctx.getChannel().write(txMessage);
            bmpSession.closeSession(ctx);
            return null;
        }
        return null;
    }
}
