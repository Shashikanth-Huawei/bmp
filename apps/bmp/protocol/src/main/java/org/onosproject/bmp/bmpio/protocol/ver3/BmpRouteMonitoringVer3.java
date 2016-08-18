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

/**
 * Implements BMP route monitor.
 */
public class BmpRouteMonitoringVer3  implements BmpRouteMonitoring {
    protected static final Logger log = LoggerFactory.getLogger(BmpRouteMonitoringVer3.class);
    private BmpHeader bmpMsgHeader;
    public static final byte PACKET_VERSION = 3;
    public static final int PACKET_MINIMUM_LENGTH = 6;
    public static final BgpType MSG_TYPE;
    /**
     * Default constructor to create a new exception.
     */
    public BmpRouteMonitoringVer3() {

    }

    public void writeTo(ChannelBuffer cb) {
        WRITER.write(cb, this);
    }

    public BmpVersion getVersion() {
        return BgpVersion.BGP_4;
    }

    public BmpType getMsgType() {
        return MSG_TYPE;
    }

    public BmpHeader getHeader() {
        return this.bmpMsgHeader;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this.getClass()).toString();
    }

    static class Writer implements BmpMessageWriter<BmpRouteMonitoringVer3> {
        Writer() {
        }

        public void write(ChannelBuffer cb, BmpRouteMonitoringVer3 message) {
            //cb.writeBytes(BmpRouteMonitoringVer3.marker, 0, 16);
            //cb.writeShort(19);
            //cb.writeByte(BgpKeepaliveMsgVer4.MSG_TYPE.getType());
        }
    }

    static class Builder implements org.onosproject.bgp.bgpio.protocol.BmpRouteMonitoring.Builder {
        BmpHeader bmpMsgHeader;

        Builder() {
        }

        public BmpRouteMonitoringVer3.Builder setHeader(BmpHeader bmpMsgHeader) {
            this.bmpMsgHeader = bmpMsgHeader;
            return this;
        }

        public BmpRouteMonitoring build() {
            return new BmpRouteMonitoringVer3();
        }
    }

    static class Reader implements BmpMessageReader<BmpRouteMonitoring> {
        Reader() {
        }

        public BmpRouteMonitoring readFrom(ChannelBuffer cb, BmpHeader bmpHeader) throws BmpParseException {
            return new BmpRouteMonitoringVer3();
        }
    }
}
