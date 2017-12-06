package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks;
import org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TestTimeRule implements TestRule {


    private final Logger log = LoggerFactory.getLogger(TestTimeRule.class);

    private final Map<String, Long> resultMap = new HashMap<>();

    private long startTime;

    private String getStatistics() {
        return resultMap
                .entrySet()
                .stream()
                .map((entry) -> String.format("%s - %s ms", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private void beforeTest() {
        startTime = System.currentTimeMillis();
    }

    protected void finished(Description description) {
        long endTime = System.currentTimeMillis();
        long testTime = endTime - startTime;
        log.info("{} - {} ms", description.getMethodName(), testTime);
        resultMap.put(description.getMethodName(), testTime);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (base instanceof RunAfterTestMethodCallbacks)
                    beforeTest();
                try {
                    base.evaluate();
                } finally {
                    if (base instanceof RunAfterTestClassCallbacks)
                        log.info(getStatistics(), base.getClass().getSimpleName());
                    if (base instanceof RunAfterTestMethodCallbacks)
                        finished(description);
                }
            }
        };
    }




}
