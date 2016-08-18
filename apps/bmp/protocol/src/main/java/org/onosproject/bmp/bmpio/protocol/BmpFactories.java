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
import org.onosproject.bmp.bmpio.protocol.ver3.BmpFactoryVer3;
import org.onosproject.bmp.bmpio.types.BmpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** BMP factories
 */
public final class BmpFactories {

    protected static final Logger log = LoggerFactory.getLogger(BmpFactories.class);

    private static final GenericReader GENERIC_READER = new GenericReader();

    private BmpFactories() {
    }

    /**
     * Returns the instance of BMP Version.
     *
     * @param version BMP version
     * @return BMP version
     */
    public static BmpFactory getFactory(BmpVersion version) {
        switch (version) {
        case BMP_3:
            return BmpFactoryVer3.INSTANCE;
        default:
            throw new IllegalArgumentException("[BmpFactory:]Unknown version: " + version);
        }
    }

    /**
     * Reader class for reading BMP messages from channel buffer.
     *
     */
    private static class GenericReader implements BmpMessageReader<BmpMessage> {

        @Override
        public BmpMessage readFrom(ChannelBuffer bb, BmpHeader bmpHeader)
                throws BmpParseException {
            BmpFactory factory;

            if (!bb.readable()) {
                log.error("Empty message received");
                throw new BmpParseException("Empty message received");
            }
            // TODO: Currently only BGP version 4 is supported
            factory = org.onosproject.bmp.bmpio.protocol.ver3.BmpFactoryVer3.INSTANCE;
            return factory.getReader().readFrom(bb, bmpHeader);
        }
    }

    /**
     * Returns BMP messsage generic reader.
     *
     * @return bmp message generic reader
     */
    public static BmpMessageReader<BmpMessage> getGenericReader() {
        return GENERIC_READER;
    }
}
