package com.czeczotka.bdd.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber" },
        glue = "com.czeczotka.bdd.steps",
        features = "classpath:cucumber/wso2ei_publish.feature"
)
public class WSO2EIPublishTest {
}
