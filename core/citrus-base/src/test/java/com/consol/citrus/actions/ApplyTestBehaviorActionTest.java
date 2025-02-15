package com.consol.citrus.actions;

import com.consol.citrus.TestAction;
import com.consol.citrus.TestActionRunner;
import com.consol.citrus.TestBehavior;
import com.consol.citrus.UnitTestSupport;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

/**
 * @author Christoph Deppisch
 */
public class ApplyTestBehaviorActionTest extends UnitTestSupport {

    @Mock
    private TestActionRunner runner;

    @Mock
    private TestAction mock;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldApply() {
        ApplyTestBehaviorAction applyBehavior = new ApplyTestBehaviorAction.Builder()
                .behavior(new FooBehavior())
                .on(runner)
                .build();
        applyBehavior.execute(context);

        verify(runner).run(mock);
    }

    private class FooBehavior implements TestBehavior {
        @Override
        public void apply(TestActionRunner runner) {
            runner.run(mock);
        }
    }
}
