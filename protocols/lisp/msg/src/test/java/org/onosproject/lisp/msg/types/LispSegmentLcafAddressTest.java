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

import com.google.common.testing.EqualsTester;
import org.junit.Before;
import org.junit.Test;
import org.onlab.packet.IpAddress;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit tests for LispSegmentLcafAddress class.
 */
public class LispSegmentLcafAddressTest {

    private LispSegmentLcafAddress address1;
    private LispSegmentLcafAddress sameAsAddress1;
    private LispSegmentLcafAddress address2;

    @Before
    public void setup() {

        LispIpv4Address ipv4Address1 = new LispIpv4Address(IpAddress.valueOf("192.168.1.1"));
        address1 = new LispSegmentLcafAddress((byte) 0x01, 1, ipv4Address1);

        sameAsAddress1 = new LispSegmentLcafAddress((byte) 0x01, 1, ipv4Address1);

        LispIpv4Address ipv4Address2 = new LispIpv4Address(IpAddress.valueOf("192.168.2.1"));
        address2 = new LispSegmentLcafAddress((byte) 0x02, 2, ipv4Address2);
    }

    @Test
    public void testEquality() {
        new EqualsTester()
                .addEqualityGroup(address1, sameAsAddress1)
                .addEqualityGroup(address2).testEquals();
    }

    @Test
    public void testConstruction() {
        LispSegmentLcafAddress segmentLcafAddress = address1;

        LispIpv4Address ipv4Address = new LispIpv4Address(IpAddress.valueOf("192.168.1.1"));

        assertThat(segmentLcafAddress.getIdMaskLength(), is((byte) 0x01));
        assertThat(segmentLcafAddress.getInstanceId(), is(1));
        assertThat(segmentLcafAddress.getAddress(), is(ipv4Address));
    }
}
