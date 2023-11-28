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
package io.streamthoughts.jikkou.extension.reconcile;

import io.streamthoughts.jikkou.core.annotation.Description;
import io.streamthoughts.jikkou.core.annotation.SupportedResource;
import io.streamthoughts.jikkou.core.config.Configuration;
import io.streamthoughts.jikkou.core.extension.ContextualExtension;
import io.streamthoughts.jikkou.core.extension.annotations.ExtensionOptionSpec;
import io.streamthoughts.jikkou.core.extension.annotations.ExtensionSpec;
import io.streamthoughts.jikkou.core.models.DefaultResourceListObject;
import io.streamthoughts.jikkou.core.models.ObjectMeta;
import io.streamthoughts.jikkou.core.models.ResourceListObject;
import io.streamthoughts.jikkou.core.reconcilier.Collector;
import io.streamthoughts.jikkou.core.selector.Selector;
import io.streamthoughts.jikkou.extension.model.V1File;
import io.streamthoughts.jikkou.extension.model.V1FileSpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SupportedResource(type = V1File.class)
@ExtensionSpec(
        options = {
                @ExtensionOptionSpec(
                        name = "directory",
                        description = "The absolute path of the directory from which to collect files",
                        type = String.class,
                        required = true
                )
        }
)
@Description("FileCollector allows listing all files in a given directory.")
public final class FileCollector extends ContextualExtension implements Collector<V1File> {

    private static final Logger LOG = LoggerFactory.getLogger(FileCollector.class);

    @Override
    public ResourceListObject<V1File> listAll(@NotNull Configuration configuration,
                                              @NotNull Selector selector) {

        // Get the 'directory' option.
        String directory = extensionContext().<String>configProperty("directory").get(configuration);

        // Collect all files.
        List<V1File> objects = Stream.of(new File(directory).listFiles())
                .filter(file -> !file.isDirectory())
                .map(file -> {
                    try {
                        Path path = file.toPath();
                        String content = Files.readString(path);
                        V1File object = new V1File(ObjectMeta
                                .builder()
                                .withName(file.getName())
                                .withAnnotation("system.jikkou.io/fileSize", Files.size(path))
                                .withAnnotation("system.jikkou.io/fileLastModifier", Files.getLastModifiedTime(path))
                                .build(),
                                new V1FileSpec(content)
                        );
                        return Optional.of(object);
                    } catch (IOException e) {
                        LOG.error("Cannot read content from file: {}", file.getName(), e);
                        return Optional.<V1File>empty();
                    }
                })
                .flatMap(Optional::stream)
                .toList();
        ObjectMeta objectMeta = ObjectMeta
                .builder()
                .withAnnotation("system.jikkou.io/directory", directory)
                .build();
        return new DefaultResourceListObject<>("FileList", "system.jikkou.io/v1", objectMeta, objects);
    }
}
