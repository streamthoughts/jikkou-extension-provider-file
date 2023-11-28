/*
 * Copyright 2023 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.streamthoughts.jikkou.extension.model;

import io.streamthoughts.jikkou.core.annotation.ApiVersion;
import io.streamthoughts.jikkou.core.annotation.Description;
import io.streamthoughts.jikkou.core.annotation.Kind;
import io.streamthoughts.jikkou.core.annotation.Names;
import io.streamthoughts.jikkou.core.annotation.Reflectable;
import io.streamthoughts.jikkou.core.annotation.Verbs;
import io.streamthoughts.jikkou.core.models.HasSpec;
import io.streamthoughts.jikkou.core.models.ObjectMeta;
import io.streamthoughts.jikkou.core.models.Verb;
import java.beans.ConstructorProperties;
import java.util.Objects;

@Reflectable
@Kind("File")
@ApiVersion("system.jikkou.io/v1")
@Verbs(Verb.LIST)
@Description("File resources provide a way of loading file content.")
@Names(plural = "files")
public final class V1File implements HasSpec<V1FileSpec> {
    private final ObjectMeta metadata;
    private final V1FileSpec spec;

    /**
     * Creates a new {@link V1File} instance.
     *
     * @param metadata The metadata.
     * @param spec     The specification.
     */
    @ConstructorProperties({
            "metadata",
            "spec"
    })
    public V1File(ObjectMeta metadata, V1FileSpec spec) {
        this.metadata = metadata;
        this.spec = spec;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public V1FileSpec getSpec() {
        return spec;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ObjectMeta getMetadata() {
        return metadata;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public V1File withMetadata(ObjectMeta metadata) {
        return new V1File(metadata, spec);
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (V1File) obj;
        return Objects.equals(this.metadata, that.metadata) &&
                Objects.equals(this.spec, that.spec);
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public int hashCode() {
        return Objects.hash(metadata, spec);
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public String toString() {
        return "V1File[" +
                "metadata=" + metadata + ", " +
                "spec=" + spec + ']';
    }

}
