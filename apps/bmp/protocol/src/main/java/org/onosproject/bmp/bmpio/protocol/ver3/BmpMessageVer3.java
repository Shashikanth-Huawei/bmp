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

package org.onosproject.bmp.bmpio.protocol.ver3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.bmp.bmpio.exceptions.BmpParseException;
import org.onosproject.bmp.bmpio.protocol.BmpFactories;
import org.onosproject.bmp.bmpio.protocol.BmpMessage;
import org.onosproject.bmp.bmpio.protocol.BmpMessageReader;
import org.onosproject.bmp.bmpio.types.BmpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides BMP messages.
 */
public abstract class BmpMessageVer3 {

    protected static final Logger log = LoggerFactory.getLogger(BmpMessageVer3.class);
    static final byte BMP_ROUTE_MONITORINING = 0;
    static final byte BMP_PEER_DOWN_NOTIFICATION = 0x1;
    static final byte BMP_STATS_REPORTS = 0x2;
    static final byte BMP_PEER_UP_NOTIFICATION = 0x3;
    static final byte BMP_INITIATION = 0x4;
    static final byte BMP_TERMINATION = 0x5;
    static final byte BMP_ROUTE_MIRRORING = 0x6;

    static final int MINIMUM_COMMON_HEADER_LENGTH = 6;

    public static final BmpMessageVer3.Reader READER = new Reader();

    /**
     * Reader class for reading BMP messages from channel buffer.
     *
     */
    static class Reader implements BmpMessageReader<BmpMessage> {
        @Override
        public BmpMessage readFrom(ChannelBuffer cb, BmpHeader bmpHeader)
                throws BmpParseException {
            try {
                switch (type) {
                case BMP_ROUTE_MONITORINING:
                    log.debug("OPEN MESSAGE is received");
                    return null; //BgpOpenMsgVer4.READER.readFrom(cb.readBytes(len), bmpHeader);
                case BMP_PEER_DOWN_NOTIFICATION:
                    log.debug("KEEPALIVE MESSAGE is received");
                    return null; //BgpKeepaliveMsgVer4.READER.readFrom(cb.readBytes(len), bmpHeader);
                case BMP_STATS_REPORTS:
                    log.debug("UPDATE MESSAGE is received");
                    return null; //BgpUpdateMsgVer4.READER.readFrom(cb.readBytes(len), bmpHeader);
                case BMP_PEER_UP_NOTIFICATION:
                    log.debug("NOTIFICATION MESSAGE is received");
                    return null; //BgpNotificationMsgVer4.READER.readFrom(cb.readBytes(len), bmpHeader);
                case BMP_INITIATION:
                    log.debug("NOTIFICATION MESSAGE is received");
                    return null; //BgpNotificationMsgVer4.READER.readFrom(cb.readBytes(len), bmpHeader);
                case BMP_TERMINATION:
                    log.debug("NOTIFICATION MESSAGE is received");
                    return null; //BgpNotificationMsgVer4.READER.readFrom(cb.readBytes(len), bmpHeader);
                case BMP_ROUTE_MIRRORING:
                    log.debug("NOTIFICATION MESSAGE is received");
                    return null; //BgpNotificationMsgVer4.READER.readFrom(cb.readBytes(len), bmpHeader);
                default:
                    return null;
                }
            } catch (IndexOutOfBoundsException e) {
                throw new BmpParseException();
            }
        }
    }
}
