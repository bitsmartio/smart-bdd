package component.results;

import io.bitsmart.bdd.report.junit5.launcher.TestLauncher;
import io.bitsmart.bdd.report.junit5.results.extension.ReportExtension;
import io.bitsmart.bdd.report.junit5.results.model.TestCaseResult;
import io.bitsmart.bdd.report.junit5.results.model.TestMethod;
import io.bitsmart.bdd.report.junit5.results.model.TestSuiteClass;
import io.bitsmart.bdd.report.junit5.results.model.TestSuiteResult;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractResultsForTestSuite {
    private TestSuiteResult testSuiteResult;

    @BeforeEach
    void beforeAll() {
        ReportExtension.getTestContext().reset();
        TestLauncher.launch(classUnderTest());
        testSuiteResult = testSuiteResult(classUnderTest());
    }

    public abstract Class<?> classUnderTest();

    public TestSuiteClass testSuiteClass() {
        return TestSuiteClass.testSuiteClass(classUnderTest());
    }

    public TestCaseResult testCaseResult(int index) {
        return testSuiteResult.getTestCaseResults().get(index);
    }

    public TestCaseResult testCaseResult(String methodName) {
        return testSuiteResult().getTestCaseResult(method(methodName));
    }

    public TestCaseResult firstTestCaseResult(String methodName) {
        return testCaseResults(methodName).get(0);
    }

    public TestCaseResult secondTestCaseResult(String methodName) {
        return testCaseResults(methodName).get(1);
    }

    public TestCaseResult thirdTestCaseResult(String methodName) {
        return testCaseResults(methodName).get(2);
    }

    public List<TestCaseResult> testCaseResults(String methodName) {
        return testSuiteResult().getTestCaseResults(method(methodName));
    }

    public TestSuiteResult testSuiteResult() {
        return testSuiteResult;
    }

    private TestSuiteResult testSuiteResult(Class<?> clazz) {
        return ReportExtension.getTestContext().getTestResults().getTestSuiteResults(TestSuiteClass.testSuiteClass(clazz));
    }

    protected TestMethod method(String name) {
        return new TestMethod(name);
    }

    protected void assertTestSuitClass(TestSuiteResult testSuiteResult, Class<?> clazz) {
        assertThat(testSuiteResult.getTestSuiteClass().getClassName()).isEqualTo(clazz.getSimpleName());
        assertThat(testSuiteResult.getTestSuiteClass().getFullyQualifiedName()).isEqualTo(clazz.getPackage().getName() + "." + clazz.getSimpleName());
        assertThat(testSuiteResult.getTestSuiteClass().getPackageName()).isEqualTo(clazz.getPackage().getName());
    }

    protected void assertCauseWithMessage(TestCaseResult result, String message) {
        Throwable cause = result.getCause().orElseThrow(() -> new RuntimeException("Expected cause"));
        assertThat(cause.getMessage()).isEqualTo(message);
        assertThat(cause.getClass()).isNotNull();
        assertThat(cause.getCause()).isNull();
        assertThat(cause.getStackTrace()).isNotNull();
    }

    protected void assertEqualsIgnoringCause(TestCaseResult actual, TestCaseResult expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "cause");
    }
}
