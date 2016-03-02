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

package com.consol.citrus.jms.config.annotation;

import com.consol.citrus.annotations.CitrusEndpointConfig;

import java.lang.annotation.*;

/**
 * @author Christoph Deppisch
 * @since 2.6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@CitrusEndpointConfig(qualifier = "endpoint.parser.jms.sync")
public @interface JmsSyncEndpointConfig {

    /**
     * Destination name.
     * @return
     */
    String destinationName() default "";

    /**
     * Destination reference.
     * @return
     */
    String destination() default "";

    /**
     * Reply destination name.
     * @return
     */
    String replyDestinationName() default "";

    /**
     * Reply destination reference.
     * @return
     */
    String replyDestination() default "";

    /**
     * Connection factory reference.
     * @return
     */
    String connectionFactory() default "";

    /**
     * JmsTemplate reference.
     * @return
     */
    String jmsTemplate() default "";

    /**
     * Message converter reference.
     * @return
     */
    String messageConverter() default "";

    /**
     * Publish subscribe domain.
     * @return
     */
    boolean pubSubDomain() default false;

    /**
     * Should use object messages.
     * @return
     */
    boolean useObjectMessages() default false;

    /**
     * Polling interval.
     * @return
     */
    int pollingInterval() default 500;

    /**
     * Message correlator.
     * @return
     */
    String correlator() default "";

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
