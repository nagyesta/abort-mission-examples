package com.github.nagyesta.abortmission.examples;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.picocontainer.PicoFactory;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
// HINT: AbortMissionHook is picked up automatically as it is in the same package
@CucumberOptions(features = "classpath:/features",
        // HINT: Add plugin for reporting
        plugin = "com.github.nagyesta.abortmission.booster.cucumber.AbortMissionPlugin",
        objectFactory = PicoFactory.class)
public class RunTest {

}
