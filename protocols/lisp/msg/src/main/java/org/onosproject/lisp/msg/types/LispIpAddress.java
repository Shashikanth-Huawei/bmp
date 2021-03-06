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
package org.onosproject.lisp.msg.types;

import org.onlab.packet.IpAddress;

/**
 * IP address that is used by LISP Locator.
 */
public abstract class LispIpAddress extends LispAfiAddress {

    protected final IpAddress address;

    /**
     * Initializes LISP locator's IP address with AFI enum.
     *
     * @param address IP address
     * @param afi AFI enum
     */
    protected LispIpAddress(IpAddress address, AddressFamilyIdentifierEnum afi) {
        super(afi);
        this.address = address;
    }

    /**
     * Obtains LISP locator's IP address.
     *
     * @return IP address
     */
    public IpAddress getAddress() {
        return address;
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return address.equals(obj);
    }

    @Override
    public String toString() {
        return address.toString();
    }
}
