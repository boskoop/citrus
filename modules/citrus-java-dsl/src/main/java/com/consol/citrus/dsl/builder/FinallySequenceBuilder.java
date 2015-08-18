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

package com.consol.citrus.dsl.builder;

import com.consol.citrus.TestAction;
import com.consol.citrus.container.TestActionContainer;
import com.consol.citrus.dsl.container.FinallySequence;
import com.consol.citrus.dsl.design.TestDesigner;
import com.consol.citrus.dsl.runner.TestRunner;

/**
 * @author Christoph Deppisch
 * @since 2.3
 */
public class FinallySequenceBuilder extends AbstractTestContainerBuilder<FinallySequence> {

    /** The test designer */
    private TestDesigner designer;

    /**
     * Default constructor using designer and action container.
     * @param designer
     * @param action
     */
    public FinallySequenceBuilder(TestDesigner designer, FinallySequence action) {
        super(action);
        this.designer = designer;
    }

    /**
     * Default constructor using test designer.
     * @param designer
     */
    public FinallySequenceBuilder(TestDesigner designer) {
        this(designer, new FinallySequence());
    }

    /**
     * Default constructor using runner and action container.
     * @param runner
     * @param action
     */
    public FinallySequenceBuilder(TestRunner runner, FinallySequence action) {
        super(runner, action);
    }

    /**
     * Default constructor using test runner.
     * @param runner
     */
    public FinallySequenceBuilder(TestRunner runner) {
        this(runner, new FinallySequence());
    }

    @Override
    public TestActionContainer actions(TestAction... actions) {
        if (runner != null) {
            return super.actions(actions);
        } else {
            for (TestAction action : actions) {
                if (action instanceof TestActionBuilder<?>) {
                    designer.build().getActions().remove(((TestActionBuilder<?>) action).build());
                    designer.build().getFinalActions().add(((TestActionBuilder<?>) action).build());
                    container.addTestAction(((TestActionBuilder<?>) action).build());
                } else if (!action.getClass().isAnonymousClass()) {
                    designer.build().getActions().remove(action);
                    designer.build().getFinalActions().add(action);
                    container.addTestAction(action);
                } else {
                    designer.build().getFinalActions().add(action);
                    container.addTestAction(action);
                }
            }

            return container;
        }
    }
}
