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
import org.onosproject.bmp.bmpio.protocol.BmpMessageWriter;
import org.onosproject.bmp.bmpio.protocol.BmpStatsReports;
import org.onosproject.bmp.bmpio.protocol.BmpVersion;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import org.onosproject.bmp.bmpio.protocol.BmpType;
import org.onosproject.bmp.bmpio.types.BmpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements BMP route monitor.
 */
public class BmpStatsReportsVer3 {

    /**
     * Default constructor to create a new exception.
     */
    public BmpStatsReportsVer3() {

    }

    public void writeTo(ChannelBuffer cb) {
        WRITER.write(cb, this);
    }

    public BmpVersion getVersion() {
        return BmpVersion.BMP_3;
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

    static class Writer implements BmpMessageWriter<BmpStatsReportsVer3> {
        Writer() {
        }

        public void write(ChannelBuffer cb, BmpStatsReportsVer3 message) {
            //cb.writeBytes(BmpRouteMonitoringVer3.marker, 0, 16);
            //cb.writeShort(19);
            //cb.writeByte(BgpKeepaliveMsgVer4.MSG_TYPE.getType());
        }
    }

    static class Builder implements BmpStatsReports.Builder {
        BmpHeader bmpMsgHeader;

        Builder() {
        }

        public BmpStatsReportsVer3.Builder setHeader(BmpHeader bmpMsgHeader) {
            this.bmpMsgHeader = bmpMsgHeader;
            return this;
        }

        public BmpStatsReports build() {
            return new BmpStatsReportsVer3();
        }
    }

    static class Reader implements BmpMessageReader<BmpStatsReports> {
        Reader() {
        }

        public BmpStatsReports readFrom(ChannelBuffer cb, BmpHeader bmpHeader) throws BmpParseException {
            return new BmpStatsReportsVer3();
        }
    }
}
