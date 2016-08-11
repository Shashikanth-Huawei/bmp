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
import org.onosproject.bmp.bmpio.BmpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class for preparing BMP messages.
 */
final class BmpMessage {
    private static final Logger log =
        LoggerFactory.getLogger(BmpMessage.class);

    /**
     * Default constructor.
     * <p>
     * The constructor is private to prevent creating an instance of
     * this utility class.
     */
    private BmpMessage() {
    }

    /**
     * Prepares BGP message.
     *
     * @param type the BMP message type
     * @param payload the message payload to transmit (BMP header excluded)
     * @return the message to transmit (BMP header included)
     */
    static ChannelBuffer prepareBmpMessage(int type, ChannelBuffer payload) {
        ChannelBuffer message =
            ChannelBuffers.buffer(BmpConstants.BGP_HEADER_LENGTH +
                                  payload.readableBytes());

        // Write the marker
        for (int i = 0; i < BmpConstants.BGP_HEADER_MARKER_LENGTH; i++) {
            message.writeByte(0xff);
        }

        // Write the rest of the BGP header
        message.writeShort(BmpConstants.BGP_HEADER_LENGTH +
                           payload.readableBytes());
        message.writeByte(type);

        // Write the payload
        message.writeBytes(payload);
        return message;
    }

    /**
     * An exception indicating a parsing error of the BMP message.
     */
    static final class BgpParseException extends Exception {
        /**
         * Default constructor.
         */
        private BgpParseException() {
            super();
        }

        /**
         * Constructor for a specific exception details message.
         *
         * @param message the message with the exception details
         */
        BgpParseException(String message) {
            super(message);
        }
    }
}
