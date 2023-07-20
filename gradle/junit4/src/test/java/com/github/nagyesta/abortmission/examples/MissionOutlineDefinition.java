package com.github.nagyesta.abortmission.examples;

import com.github.nagyesta.abortmission.booster.junit4.extractor.CategoryDependencyNameExtractor;
import com.github.nagyesta.abortmission.core.AbortMissionCommandOps;
import com.github.nagyesta.abortmission.core.matcher.MissionHealthCheckMatcher;
import com.github.nagyesta.abortmission.core.outline.MissionOutline;

import java.util.Map;
import java.util.function.Consumer;

import static com.github.nagyesta.abortmission.core.MissionControl.*;

public class MissionOutlineDefinition extends MissionOutline {
    @Override
    protected Map<String, Consumer<AbortMissionCommandOps>> defineOutline() {
        // HINT: use the default, "shared" namespace by not adding a name
        return Map.of("", ops -> {
            final CategoryDependencyNameExtractor categoryNames = new CategoryDependencyNameExtractor();
            // Note: More kinds of matchers available.
            // See the methods of the builder returned by the matcher() method.
            final MissionHealthCheckMatcher endToEnd = matcher()
                    .dependencyWith("EndToEnd").extractor(categoryNames).build();
            final MissionHealthCheckMatcher apiKeyMissionDependency = matcher()
                    .dependency("api-key").build();
            ops.registerHealthCheck(percentageBasedEvaluator(
                    matcher().or(endToEnd)
                            .orAtLast(apiKeyMissionDependency).build())
                    .abortThreshold(25) // abort if failure percentage is higher than 25%
                    .burnInTestCount(1) // execute 1 test before evaluating the threshold
                    .build());
            ops.registerHealthCheck(reportOnlyEvaluator(matcher().anyClass().build()).build());
        });
    }
}
