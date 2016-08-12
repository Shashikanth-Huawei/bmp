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
package org.onosproject.bmp.bmpio;

/**
 * BMP related constants.
 */
public final class BmpConstants {
    /**
     * Default constructor.
     * <p>
     * The constructor is private to prevent creating an instance of
     * this utility class.
     */
    private BmpConstants() {
    }

    /** BMP port number (RFC 4271). */
    public static final int BMP_PORT = 179;

    /** BMP version. */
    public static final int BMP_VERSION = 4;

    /** BMP route monitoring message type. */
    public static final int BMP_ROUTE_MONITORING = 0;

    /** BMP peer down notification message type. */
    public static final int BMP_PEER_DOWN_NOTIFICATION = 1;

    /** BMP stats reports message type. */
    public static final int BMP_STATS_REPORTS = 2;

    /** BMP peer up notification message type. */
    public static final int BMP_PEER_UP_NOTIFICATION = 3;

    /** BMP initiation message type. */
    public static final int BMP_INITIATION = 4;

    /** BMP termination message type. */
    public static final int BMP_TERMINATION = 5;

    /** BMP route mirroring message type. */
    public static final int BMP_ROUTE_MIRRORING = 6;

    /** BMP Header Marker field length. */
    public static final int BMP_HEADER_MARKER_LENGTH = 16;

    /** BMP Header length. */
    public static final int BMP_HEADER_LENGTH = 19;

    /** BMP message maximum length. */
    public static final int BMP_MESSAGE_MAX_LENGTH = 4096;

    /**
     * BMP NOTIFICATION related constants.
     */
    public static final class Notifications {
        /**
         * Default constructor.
         * <p>
         * The constructor is private to prevent creating an instance of
         * this utility class.
         */
        private Notifications() {
        }

        /**
         * BGP NOTIFICATION: message header error constants.
         */
        public static final class MessageHeaderError {
            /**
             * Default constructor.
             * <p>
             * The constructor is private to prevent creating an instance of
             * this utility class.
             */
            private MessageHeaderError() {
            }

            /** Message Header Error code. */
            public static final int ERROR_CODE = 1;

            /** Message Header Error subcode: Connection Not Synchronized. */
            public static final int CONNECTION_NOT_SYNCHRONIZED = 1;

            /** Message Header Error subcode: Bad Message Length. */
            public static final int BAD_MESSAGE_LENGTH = 2;

            /** Message Header Error subcode: Bad Message Type. */
            public static final int BAD_MESSAGE_TYPE = 3;
        }

        /**
         * BGP NOTIFICATION: OPEN message error constants.
         */
        public static final class OpenMessageError {
            /**
             * Default constructor.
             * <p>
             * The constructor is private to prevent creating an instance of
             * this utility class.
             */
            private OpenMessageError() {
            }

            /** OPEN Message Error code. */
            public static final int ERROR_CODE = 2;

            /** OPEN Message Error subcode: Unsupported Version Number. */
            public static final int UNSUPPORTED_VERSION_NUMBER = 1;

            /** OPEN Message Error subcode: Bad PEER AS. */
            public static final int BAD_PEER_AS = 2;

            /** OPEN Message Error subcode: Unacceptable Hold Time. */
            public static final int UNACCEPTABLE_HOLD_TIME = 6;
        }

        /**
         * BMP NOTIFICATION: UPDATE message error constants.
         */
        public static final class UpdateMessageError {
            /**
             * Default constructor.
             * <p>
             * The constructor is private to prevent creating an instance of
             * this utility class.
             */
            private UpdateMessageError() {
            }

            /** UPDATE Message Error code. */
            public static final int ERROR_CODE = 3;

            /** UPDATE Message Error subcode: Malformed Attribute List. */
            public static final int MALFORMED_ATTRIBUTE_LIST = 1;

            /** UPDATE Message Error subcode: Unrecognized Well-known Attribute. */
            public static final int UNRECOGNIZED_WELL_KNOWN_ATTRIBUTE = 2;

            /** UPDATE Message Error subcode: Missing Well-known Attribute. */
            public static final int MISSING_WELL_KNOWN_ATTRIBUTE = 3;

           /** UPDATE Message Error subcode: Attribute Flags Error. */
            public static final int ATTRIBUTE_FLAGS_ERROR = 4;

            /** UPDATE Message Error subcode: Attribute Length Error. */
            public static final int ATTRIBUTE_LENGTH_ERROR = 5;

            /** UPDATE Message Error subcode: Invalid ORIGIN Attribute. */
            public static final int INVALID_ORIGIN_ATTRIBUTE = 6;

            /** UPDATE Message Error subcode: Invalid NEXT_HOP Attribute. */
            public static final int INVALID_NEXT_HOP_ATTRIBUTE = 8;

            /** UPDATE Message Error subcode: Optional Attribute Error. Unused. */
            public static final int OPTIONAL_ATTRIBUTE_ERROR = 9;

            /** UPDATE Message Error subcode: Invalid Network Field. */
            public static final int INVALID_NETWORK_FIELD = 10;

            /** UPDATE Message Error subcode: Malformed AS_PATH. */
            public static final int MALFORMED_AS_PATH = 11;
        }
    }
}
