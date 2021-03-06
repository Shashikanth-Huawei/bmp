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
package org.onosproject.bgp.bgpio.types;

import org.junit.Test;
import org.onlab.packet.Ip6Address;

import com.google.common.testing.EqualsTester;

/**
 * Test for IPv6Address Tlv.
 */
public class IPv6AddressTest {
    private final Ip6Address value1 = Ip6Address.valueOf("2001:db8:0:0:0:0:2:1");
    private final Ip6Address value2 = Ip6Address.valueOf("2001:db8:0:0:0:0:2:1");
    private final IPv6AddressTlv tlv1 = IPv6AddressTlv.of(value1, (short) 261);
    private final IPv6AddressTlv sameAsTlv1 = IPv6AddressTlv.of(value1, (short) 261);
    private final IPv6AddressTlv tlv2 = IPv6AddressTlv.of(value2, (short) 262);

    @Test
    public void basics() {
        new EqualsTester()
        .addEqualityGroup(tlv1, sameAsTlv1)
        .addEqualityGroup(tlv2)
        .testEquals();
    }
}
