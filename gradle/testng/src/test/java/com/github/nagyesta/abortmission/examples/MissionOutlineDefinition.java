package com.github.nagyesta.abortmission.examples;

import com.github.nagyesta.abortmission.booster.testng.extractor.GroupDependencyNameExtractor;
import com.github.nagyesta.abortmission.core.AbortMissionCommandOps;
import com.github.nagyesta.abortmission.core.matcher.MissionHealthCheckMatcher;
import com.github.nagyesta.abortmission.core.outline.MissionOutline;

import java.util.Map;
import java.util.function.Consumer;

import static com.github.nagyesta.abortmission.core.MissionControl.*;

public class MissionOutlineDefinition extends MissionOutline {
    @Override
    protected Map<String, Consumer<AbortMissionCommandOps>> defineOutline() {
        // use the default, "shared" namespace by not adding a name
        return Map.of("", ops -> {
            final GroupDependencyNameExtractor groupNames = new GroupDependencyNameExtractor();
            // Note: More kinds of matchers available.
            // See the methods of the builder returned by the matcher() method.
            final MissionHealthCheckMatcher endToEnd = matcher()
                    .dependencyWith("end-to-end").extractor(groupNames).build();
            final MissionHealthCheckMatcher apiKeyMissionDependency = matcher()
                    .dependency("api-key").build();
            ops.registerHealthCheck(percentageBasedEvaluator(
                    matcher().or(endToEnd)
                            .orAtLast(apiKeyMissionDependency).build())
                    .abortThreshold(1)
                    .burnInTestCount(1)
                    .build());
            ops.registerHealthCheck(reportOnlyEvaluator(matcher().anyClass().build()).build());
        });
    }
}
