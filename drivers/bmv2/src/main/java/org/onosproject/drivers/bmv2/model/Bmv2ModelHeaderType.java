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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.List;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * BMv2 model header type.
 */
public final class Bmv2ModelHeaderType {

    private final String name;
    private final int id;
    private final LinkedHashMap<String, Bmv2ModelFieldType> fields = Maps.newLinkedHashMap();

    /**
     * Creates a new header type instance.
     *
     * @param name       name
     * @param id         id
     * @param fieldTypes fields
     */
    protected Bmv2ModelHeaderType(String name, int id, List<Bmv2ModelFieldType> fieldTypes) {
        this.name = name;
        this.id = id;
        fieldTypes.forEach(f -> this.fields.put(f.name(), f));
    }

    /**
     * Returns this header type name.
     *
     * @return name
     */
    public String name() {
        return name;
    }

    /**
     * Returns this header type id.
     *
     * @return id
     */
    public int id() {
        return id;
    }

    /**
     * Returns this header type's field defined by the passed name, null if
     * not present.
     *
     * @param fieldName field name
     * @return field or null
     */
    public Bmv2ModelFieldType field(String fieldName) {
        return fields.get(fieldName);
    }

    /**
     * Return and immutable list of header fields for this header
     * type. The list is ordered according to the values defined in the
     * model.
     *
     * @return list of fields
     */
    public List<Bmv2ModelFieldType> fields() {
        return ImmutableList.copyOf(fields.values());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, id, fields);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Bmv2ModelHeaderType other = (Bmv2ModelHeaderType) obj;
        return Objects.equal(this.name, other.name)
                && Objects.equal(this.id, other.id)
                && Objects.equal(this.fields, other.fields);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("name", name)
                .add("id", id)
                .add("fields", fields)
                .toString();
    }
}
