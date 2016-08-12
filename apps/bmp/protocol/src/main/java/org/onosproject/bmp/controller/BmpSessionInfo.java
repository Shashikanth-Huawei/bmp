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

import org.onlab.packet.Ip4Address;

import java.net.SocketAddress;

/**
 * Class for keeping information about a BMP session.
 *
 * There are two instances per each BMP peer session: one to keep the local
 * information about the BGP session, and another to keep information about
 * the remote BMP peer.
 */
public class BmpSessionInfo {
    private SocketAddress address;              // IP addr/port
    private Ip4Address ip4Address;              // IPv4 address
    private int bmpVersion;                     // 1 octet

    /**
     * Gets the BMP session address: local or remote.
     *
     * @return the BMP session address
     */
    public SocketAddress address() {
        return this.address;
    }

    /**
     * Sets the BMP session address: local or remote.
     *
     * @param address the BMP session address to set
     */
    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    /**
     * Gets the BMP session IPv4 address: local or remote.
     *
     * @return the BMP session IPv4 address
     */
    public Ip4Address ip4Address() {
        return this.ip4Address;
    }

    /**
     * Sets the BMP session IPv4 address: local or remote.
     *
     * @param ip4Address the BMP session IPv4 address to set
     */
    public void setIp4Address(Ip4Address ip4Address) {
        this.ip4Address = ip4Address;
    }

    /**
     * Gets the BMP session BMP version: local or remote.
     *
     * @return the BMP session BMP version
     */
    public int bmpVersion() {
        return this.bmpVersion;
    }
}
