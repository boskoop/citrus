/*
 * Copyright 2006-2016 the original author or authors.
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

package com.consol.citrus.ws.config.annotation;

import com.consol.citrus.annotations.CitrusEndpointConfig;
import com.consol.citrus.message.ErrorHandlingStrategy;

import java.lang.annotation.*;

/**
 * @author Christoph Deppisch
 * @since 2.6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@CitrusEndpointConfig(qualifier = "endpoint.parser.ws.client")
public @interface WebServiceClientConfig {
    /**
     * Request uri.
     * @return
     */
    String requestUrl();

    /**
     * WebServiceTemplate
     * @return
     */
    String webServiceTemplate() default "";

    /**
     * Message factory.
     * @return
     */
    String messageFactory() default "";

    /**
     * Endpoint uri resolver.
     * @return
     */
    String endpointResolver() default "";

    /**
     * Message sender.
     * @return
     */
    String messageSender() default "";

    /**
     * Message converter.
     * @return
     */
    String messageConverter() default  "";

    /**
     * Message correlator.
     * @return
     */
    String correlator() default "";

    /**
     * Polling interval.
     * @return
     */
    int pollingInterval() default 500;


    /**
     * Fault handling strategy.
     * @return
     */
    ErrorHandlingStrategy faultStrategy() default ErrorHandlingStrategy.THROWS_EXCEPTION;

    /**
     * Client interceptor.
     * @return
     */
    String interceptor() default "";

    /**
     * Client interceptors.
     * @return
     */
    String[] interceptors() default {};

    /**
     * Timeout.
     * @return
     */
    long timeout() default 5000L;

    /**
     * Test actor.
     * @return
     */
    String actor() default "";
}
