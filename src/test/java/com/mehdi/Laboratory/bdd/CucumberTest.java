package com.mehdi.Laboratory.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "com.mehdi.Laboratory",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
@TestExecutionListeners({
        DirtiesContextBeforeModesTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
public class CucumberTest {
}
