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
import org.onosproject.bmp.bmpio.types.BmpHeader;

/**
 * Abstraction of an entity providing BMP route mirroring message.
 */
public interface BmpRouteMirroring extends BmpMessage {

    @Override
    BmpVersion getVersion();

    @Override
    BmpType getType();

    @Override
    void writeTo(ChannelBuffer channelBuffer);

    @Override
    BmpHeader getHeader();

    /**
     * Builder interface with get and set functions to build BmpRouteMirroring message.
     */
    interface Builder extends BmpMessage.Builder {
        @Override
        BmpRouteMirroring build();

        @Override
        Builder setHeader(BmpHeader bmpMsgHeader);
    }
}
