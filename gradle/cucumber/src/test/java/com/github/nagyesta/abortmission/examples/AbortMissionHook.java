package com.github.nagyesta.abortmission.examples;

import com.github.nagyesta.abortmission.booster.cucumber.LaunchAbortHook;
import com.github.nagyesta.abortmission.booster.cucumber.matcher.TagDependencyNameExtractor;
import com.github.nagyesta.abortmission.core.AbortMissionCommandOps;
import com.github.nagyesta.abortmission.core.matcher.MissionHealthCheckMatcher;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.AssumptionViolatedException;

import java.util.Map;
import java.util.function.Consumer;

import static com.github.nagyesta.abortmission.core.MissionControl.*;

public class AbortMissionHook extends LaunchAbortHook {
    @Override
    protected Map<String, Consumer<AbortMissionCommandOps>> defineOutline() {
        // HINT: use the default, "shared" namespace by not adding a name
        return Map.of("", ops -> {
            final TagDependencyNameExtractor tagNames = new TagDependencyNameExtractor();
            // Note: More kinds of matchers available.
            // See anyScenarioMatcher() , scenarioNameMatcher() and scenarioUriMatcher()
            final MissionHealthCheckMatcher endToEndMatcher = matcher()
                    .dependencyWith("EndToEnd").extractor(tagNames).build();
            ops.registerHealthCheck(percentageBasedEvaluator(endToEndMatcher)
                    .abortThreshold(25) // abort if failure percentage is higher than 25%
                    .burnInTestCount(1) // execute 1 test before evaluating the threshold
                    .build());
            ops.registerHealthCheck(reportOnlyEvaluator(anyScenarioMatcher()).build());
        });
    }

    @Before
    @Override
    public void beforeScenario(final Scenario scenario) {
        doBeforeScenario(scenario);
    }

    @After
    @Override
    public void afterScenario(final Scenario scenario) {
        doAfterScenario(scenario);
    }

    @Override
    protected void doAbort() {
        throw new AssumptionViolatedException("Aborting as the launch failure threshold is reached.");
    }
}
