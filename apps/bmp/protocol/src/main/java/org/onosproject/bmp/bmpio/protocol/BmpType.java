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

/**
 * Enum to Provide the Different types of BMP messages.
 */
public enum BmpType {

    BMP_ROUTE_MONITORING(0), BMP_PEER_DOWN_NOTIFICATION(1), BMP_STATS_REPORTS(2), BMP_PEER_UP_NOTIFICATION(3), BMP_INITIATION(4),
    BMP_TERMINATION(5), BMP_ROUTE_MIRRORING(6);

    int value;

    /**
     * Assign value with the value val as the types of BMP message.
     *
     * @param val type of BMP message
     */
    BmpType(int val) {
        value = val;
    }

    /**
     * Returns value as type of BMP message.
     *
     * @return value type of BMP message
     */
    public byte getType() {
        return (byte) value;
    }
}
