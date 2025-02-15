/*
 * Copyright 2006-2015 the original author or authors.
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

package com.consol.citrus.config;

import com.consol.citrus.CitrusSpringSettings;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * @author Christoph Deppisch
 * @since 2.4
 */
public class CitrusConfigImport implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        if (StringUtils.hasText(CitrusSpringSettings.DEFAULT_APPLICATION_CONTEXT_CLASS)) {
            return new String[] { CitrusSpringSettings.DEFAULT_APPLICATION_CONTEXT_CLASS };
        } else {
            return new String[] {};
        }
    }
}
