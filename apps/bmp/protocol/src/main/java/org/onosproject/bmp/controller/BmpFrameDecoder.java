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

        log.trace("BGP Peer: decode(): remoteAddr = {} localAddr = {} " +
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

        //
        // Read and check the BGP message Marker field: it must be all ones
        // (See RFC 4271, Section 4.1)
        //
        byte[] marker = new byte[BmpConstants.BMP_HEADER_MARKER_LENGTH];
        buf.readBytes(marker);
        for (int i = 0; i < marker.length; i++) {
            if (marker[i] != (byte) 0xff) {
                log.debug("BGP RX Error: invalid marker {} at position {}",
                          marker[i], i);
                //
                // ERROR: Connection Not Synchronized
                //
                // Send NOTIFICATION and close the connection
                int errorCode = BmpConstants.Notifications.MessageHeaderError.ERROR_CODE;
                int errorSubcode =
                    BmpConstants.Notifications.MessageHeaderError.CONNECTION_NOT_SYNCHRONIZED;
                ChannelBuffer txMessage = null; /* =
                    BmpNotification.prepareBmpNotification(errorCode,
                                                           errorSubcode,
                                                           null);*/
                ctx.getChannel().write(txMessage);
                bmpSession.closeSession(ctx);
                return null;
            }
        }

        //
        // Read and check the BGP message Length field
        //
        int length = buf.readUnsignedShort();
        if ((length < BmpConstants.BMP_HEADER_LENGTH) ||
            (length > BmpConstants.BMP_MESSAGE_MAX_LENGTH)) {
            log.debug("BMP RX Error: invalid Length field {}. " +
                      "must be between {} and {}",
                      length,
                      BmpConstants.BMP_HEADER_LENGTH,
                      BmpConstants.BMP_MESSAGE_MAX_LENGTH);
            //
            // ERROR: Bad Message Length
            //
            // Send NOTIFICATION and close the connection
            ChannelBuffer txMessage = null; /*
                BmpNotification.prepareBmpNotificationBadMessageLength(length);*/
            ctx.getChannel().write(txMessage);
            bmpSession.closeSession(ctx);
            return null;
        }

        //
        // Test whether the rest of the message is received:
        // So far we have read the Marker (16 octets) and the
        // Length (2 octets) fields.
        //
        int remainingMessageLen =
            length - BmpConstants.BMP_HEADER_MARKER_LENGTH - 2;
        if (buf.readableBytes() < remainingMessageLen) {
            // No enough data received
            buf.resetReaderIndex();
            return null;
        }

        //
        // Read the BGP message Type field, and process based on that type
        //
        int type = buf.readUnsignedByte();
        remainingMessageLen--;      // Adjust after reading the type
        ChannelBuffer message = buf.readBytes(remainingMessageLen);

        //
        // Process the remaining of the message based on the message type
        //
        switch (type) {
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
            data.writeByte(type);
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
