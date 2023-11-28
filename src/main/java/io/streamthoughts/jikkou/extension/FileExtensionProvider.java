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
package io.streamthoughts.jikkou.extension;

import io.streamthoughts.jikkou.core.extension.ExtensionRegistry;
import io.streamthoughts.jikkou.core.resource.ResourceRegistry;
import io.streamthoughts.jikkou.extension.model.V1File;
import io.streamthoughts.jikkou.extension.reconcile.FileCollector;
import io.streamthoughts.jikkou.spi.ExtensionProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Extension Provider for File.
 */
public final class FileExtensionProvider implements ExtensionProvider {

    @Override
    public void registerExtensions(@NotNull ExtensionRegistry registry) {
        registry.register(FileCollector.class, FileCollector::new);
    }

    @Override
    public void registerResources(@NotNull ResourceRegistry registry) {
        registry.register(V1File.class);
    }
}
