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

package org.onosproject.bmp.bmpio.protocol.ver3;

import org.onosproject.bmp.bmpio.protocol.BmpFactory;
import org.onosproject.bmp.bmpio.protocol.BmpRouteMonitoring;
import org.onosproject.bmp.bmpio.protocol.BmpMessage;
import org.onosproject.bmp.bmpio.protocol.BmpMessageReader;
import org.onosproject.bmp.bmpio.protocol.BmpPeerDownNotification;
import org.onosproject.bmp.bmpio.protocol.BmpStatsReports;
import org.onosproject.bmp.bmpio.protocol.BmpPeerUpNotification;
import org.onosproject.bmp.bmpio.protocol.BmpInitiation;
import org.onosproject.bmp.bmpio.protocol.BmpTermination;
import org.onosproject.bmp.bmpio.protocol.BmpRouteMirroring;
import org.onosproject.bmp.bmpio.protocol.BmpVersion;

/**
 * Provides BMP Factory and returns builder classes for all objects and messages.
 */
public class BmpFactoryVer3 implements BmpFactory {

    public static final BmpFactoryVer3 INSTANCE = new BmpFactoryVer3();

    @Override
    public BmpRouteMonitoring.Builder routeMonitorMessageBuilder() {
        return new BmpRouteMonitoringVer3.Builder();
    }

    @Override
    public BmpPeerDownNotification.Builder peerDownNotificationMessageBuilder() {
        return new BmpPeerDownNotificationVer3.Builder();
    }

    @Override
    public BmpStatsReports.Builder statsReportsMessageBuilder() {
        return new BmpStatsReportsVer3.Builder();
    }

    @Override
    public BmpPeerUpNotification.Builder peerUpNotificationMessageBuilder() {
        return new BgpUpdateMsgVer4.Builder();
    }

    @Override
    public BmpInitiation.Builder initiationMessageBuilder() {
        return new BmpInitiationVer3.Builder();
    }

    @Override
    public BmpTermination.Builder terminationMessageBuilder() {
        return new BmpTerminationVer3.Builder();
    }

    @Override
    public BmpRouteMirroring.Builder routeMirroringMessageBuilder() {
        return new BmpRouteMirroringVer3.Builder();
    }

    @Override
    public BmpMessageReader<BmpMessage> getReader() {
        return BmpMessageVer3.READER;
    }

    @Override
    public BmpVersion getVersion() {
        return BmpVersion.BMP_3;
    }
}
