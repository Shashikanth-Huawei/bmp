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

package org.onosproject.bgp.bgpio.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.bgp.bgpio.exceptions.BgpParseException;

/**
 * Abstraction of an entity providing functionality to write byte streams of
 * Messages to channel buffer.
 */
public interface Writeable {

    /**
     * Writes byte streams of messages to channel buffer.
     *
     * @param cb channelBuffer
     * @throws BgpParseException when error occurs while writing BGP message to channel buffer
     */
    void writeTo(ChannelBuffer cb) throws BgpParseException;
}