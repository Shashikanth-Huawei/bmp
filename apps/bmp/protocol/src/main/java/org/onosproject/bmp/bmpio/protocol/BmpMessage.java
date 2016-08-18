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

package org.onosproject.bmp.bmpio.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.bmp.bmpio.exceptions.BmpParseException;
import org.onosproject.bmp.bmpio.types.BmpHeader;

/**
 * Abstraction of an entity providing BMP Messages.
 */
public interface BmpMessage extends Writeable {
    /**
     * Returns BMP Header of BMP Message.
     *
     * @return BMP Header of BMP Message
     */
    BmpHeader getHeader();

    /**
     * Returns version of BGP Message.
     *
     * @return version of BGP Message
     */
    BmpVersion getVersion();

    /**
     * Returns BMP Type of BMP Message.
     *
     * @return BMP Type of BMP Message
     */
    BmpType getMsgType();

    @Override
    void writeTo(ChannelBuffer cb) throws BmpParseException;

    /**
     * Builder interface with get and set functions to build BMP Message.
     */
    interface Builder {
        /**
         * Builds BMP Message.
         *
         * @return BMP Message
         * @throws BmpParseException while building bmp message
         */
        BmpMessage build() throws BmpParseException;

        /**
         * Sets BgpHeader and return its builder.
         *
         * @param bmpMsgHeader BMP Message Header
         * @return builder by setting BMP message header
         */
        Builder setHeader(BmpHeader bmpMsgHeader);
    }
}
