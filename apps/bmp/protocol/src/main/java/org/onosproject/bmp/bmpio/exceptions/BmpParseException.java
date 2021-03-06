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

package org.onosproject.bmp.bmpio.exceptions;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Custom Exception for BMP IO.
 */
public class BmpParseException extends Exception {

    private static final long serialVersionUID = 1L;
    private byte errorCode;
    private byte errorSubCode;
    private ChannelBuffer data;

    /**
     * Default constructor to create a new exception.
     */
    public BmpParseException() {
        super();
    }

    /**
     * Constructor to create exception from message and cause.
     *
     * @param message  the detail of exception in string
     * @param cause underlying cause of the error
     */
    public BmpParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor to create exception from message.
     *
     * @param message the detail of exception in string
     */
    public BmpParseException(final String message) {
        super(message);
    }

    /**
     * Constructor to create exception from cause.
     *
     * @param cause underlying cause of the error
     */
    public BmpParseException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor to create exception from error code and error subcode.
     *
     * @param errorCode error code of BMP message
     * @param errorSubCode error subcode of BMP message
     * @param data error data of BMP message
     */
    public BmpParseException(final byte errorCode, final byte errorSubCode, final ChannelBuffer data) {
        super();
        this.errorCode = errorCode;
        this.errorSubCode = errorSubCode;
        this.data = data;
    }

    /**
     * Returns errorcode for this exception.
     *
     * @return errorcode for this exception
     */
    public byte getErrorCode() {
        return this.errorCode;
    }

    /**
     * Returns error Subcode for this exception.
     *
     * @return error Subcode for this exception
     */
    public byte getErrorSubCode() {
        return this.errorSubCode;
    }

    /**
     * Returns error data for this exception.
     *
     * @return error data for this exception
     */
    public ChannelBuffer getData() {
        return this.data;
    }
}
