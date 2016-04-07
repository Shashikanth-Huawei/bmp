/*
 * Copyright 2014-2016 Open Networking Laboratory
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

package org.onosproject.drivers.bmv2.model;

import com.google.common.base.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * BMv2 model header type field.
 */
public final class Bmv2ModelFieldType {

    private final String name;
    private final int bitWidth;

    protected Bmv2ModelFieldType(String name, int bitWidth) {
        this.name = name;
        this.bitWidth = bitWidth;
    }

    /**
     * Returns the name of this header type field.
     *
     * @return a string value
     */
    public String name() {
        return name;
    }

    /**
     * Returns the bit width of this header type field.
     *
     * @return an integer value
     */
    public int bitWidth() {
        return bitWidth;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, bitWidth);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Bmv2ModelFieldType other = (Bmv2ModelFieldType) obj;
        return Objects.equal(this.name, other.name)
                && Objects.equal(this.bitWidth, other.bitWidth);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("name", name)
                .add("bitWidth", bitWidth)
                .toString();
    }
}
