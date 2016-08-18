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
 * Abstraction of an message factory providing builder functions to BMP messages
 * and objects.
 *
 */
public interface BmpFactory {

    /**
     * Gets the builder object for a open message.
     *
     * @return builder object for open message
     */
    BmpRouteMonitoring.Builder routeMonitorMessageBuilder();

    /**
     * Gets the builder object for a keepalive message.
     *
     * @return builder object for keepalive message
     */
    BmpPeerDownNotification.Builder peerDownNotificationMessageBuilder();

    /**
     * Gets the builder object for a notification message.
     *
     * @return builder object for notification message.
     */
    BmpStatsReports.Builder statsReportsMessageBuilder();

    /**
     * Gets the builder object for a update message.
     *
     * @return builder object for update message
     */
    BmpPeerUpNotification.Builder peerUpNotificationMessageBuilder();

    /**
     * Gets the builder object for a update message.
     *
     * @return builder object for update message
     */
    BmpInitiation.Builder initiationMessageBuilder();

    /**
     * Gets the builder object for a update message.
     *
     * @return builder object for update message
     */
    BmpTermination.Builder terminationMessageBuilder();

    /**
     * Gets the builder object for a update message.
     *
     * @return builder object for update message
     */
    BmpRouteMirroring.Builder routeMirroringMessageBuilder();

    /**
     * Gets the BGP message reader.
     *
     * @return BGP message reader
     */
    BmpMessageReader<BmpMessage> getReader();

    /**
     * Returns BMP version.
     *
     * @return BMP version
     */
    BmpVersion getVersion();
}
