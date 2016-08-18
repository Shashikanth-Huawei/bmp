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
package org.onosproject.bmp.bmpio.types;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides BMP message header which is common for all the Messages.
 */

public class BmpHeader {
    protected static final Logger log = LoggerFactory.getLogger(BmpHeader.class);

    public static final short DEFAULT_HEADER_LENGTH = 6;

    private byte version;
    private int msgLength;
    private byte msgType;

    /**
     * Reset fields.
     */
    public BmpHeader() {
        this.version = 0;
        this.msgLength = 0;
        this.msgType = 0;
    }

    /**
     * Constructors to initialize parameters.
     *
     * @param version field in BMP header
     * @param msgLength message length
     * @param msgType message type
     */
    public BmpHeader(byte version, int msgLength, byte msgType) {
        this.version = version;
        this.msgLength = msgLength;
        this.msgType = msgType;
    }

    /**
     * Sets version field.
     *
     * @param version version field
     */
    public void setVersion(byte version) {
        this.version = version;
    }

    /**
     * Sets message type.
     *
     * @param msgType message type
     */
    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    /**
     * Sets message length.
     *
     * @param value message length
     */
    public void setMsgLength(short msgLength) {
        this.msgLength = msgLength;
    }

    /**
     * Returns message length.
     *
     * @return message length
     */
    public int getMsgLength() {
        return this.msgLength;
    }

    /**
     * Returns message version.
     *
     * @return message version
     */
    public byte getVersion() {
        return this.version;
    }

    /**
     * Returns message type.
     *
     * @return message type
     */
    public byte getMsgType() {
        return this.msgType;
    }

    /**
     * Writes Byte stream of BMP header to channel buffer.
     *
     * @param cb ChannelBuffer
     * @return length index of message header
     */
    public int write(ChannelBuffer cb) {
        int headerLenIndex = cb.writerIndex();
        
        cb.writeByte(version);
        cb.writeByte(msgLength);
        cb.writeByte(msgType);
        return headerLenIndex;
    }

    /**
     * Read from channel buffer and Returns BMP header.
     *
     * @param cb ChannelBuffer
     * @return object of BGPHeader
     */
    public static BmpHeader read(ChannelBuffer cb) {
        return new BmpHeader(cb.readByte(), cb.readInt(), cb.readByte());
    }
}
