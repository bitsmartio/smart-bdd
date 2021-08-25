package component.results.scenarios;

import component.results.AbstractResultsForTestSuite;
import io.bitsmart.bdd.report.junit5.results.model.notes.Notes;
import io.bitsmart.bdd.report.junit5.results.model.TestCaseResult;
import io.bitsmart.bdd.report.junit5.results.model.TestCaseResultBuilder;
import io.bitsmart.bdd.report.junit5.results.model.TestSuiteResultsMetadata;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shared.undertest.FailedTestCasesUnderTest;

import java.util.List;

import static io.bitsmart.bdd.report.junit5.results.model.TestCaseResultBuilder.aTestCaseResult;
import static io.bitsmart.bdd.report.junit5.results.model.TestCaseResultStatus.FAILED;
import static io.bitsmart.bdd.report.junit5.results.model.TestSuiteResultsMetadataBuilder.aTestSuiteResultsMetadata;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class FailedResultsTest extends AbstractResultsForTestSuite {

    @BeforeAll
    public static void enableTest() {
        FailedTestCasesUnderTest.setEnabled(true);
    }

    @AfterAll
    public static void disableTest() {
        FailedTestCasesUnderTest.setEnabled(false);
    }

    @Override
    public Class<?> classUnderTest() {
        return FailedTestCasesUnderTest.class;
    }

    @Test
    void verifyResultsForFailedTestCases() {
        assertThat(testSuiteResult().getMetadata()).isEqualTo(
            aTestSuiteResultsMetadata()
                .withTestCaseCount(4)
                .withFailedCount(4)
                .build());

        assertThat(testSuiteResult().getMethods()).containsExactlyInAnyOrder(
            method("testMethod"),
            method("paramTest"),
            method("paramTest"),
            method("paramTest")
        );

        TestCaseResult testMethod = testSuiteResult().getTestCaseResult(method("testMethod"));
        assertEqualsIgnoringCause(testMethod, aFailedTestMethod());
        assertCauseWithMessage(testMethod, "\n" + "Expecting:\n" + " <true>\n" + "to be equal to:\n" + " <false>\n" + "but was not.");

        assertEqualsIgnoringCause(firstTestCaseResult("paramTest"),
            aFailedParamTestCaseResult()
                .withWordify("Failing assertion with value 1")
                .withArgs(singletonList("value 1"))
                .withName("paramTest value 1")
                .build()
        );
        assertCauseWithMessage(firstTestCaseResult("paramTest"), "\nExpecting:\n <\"value 1\">\nto be equal to:\n <null>\nbut was not.");

        assertEqualsIgnoringCause(secondTestCaseResult("paramTest"),
            aFailedParamTestCaseResult()
                .withWordify("Failing assertion with value 2")
                .withArgs(singletonList("value 2"))
                .withName("paramTest value 2")
                .build()
        );
        assertCauseWithMessage(secondTestCaseResult("paramTest"), "\nExpecting:\n <\"value 2\">\nto be equal to:\n <null>\nbut was not.");

        assertEqualsIgnoringCause(thirdTestCaseResult("paramTest"),
            aFailedParamTestCaseResult()
                .withWordify("Failing assertion with value 3")
                .withArgs(singletonList("value 3"))
                .withName("paramTest value 3")
                .build()
        );
        assertCauseWithMessage(thirdTestCaseResult("paramTest"), "\nExpecting:\n <\"value 3\">\nto be equal to:\n <null>\nbut was not.");
    }

    private TestCaseResultBuilder aFailedParamTestCaseResult() {
        return aTestCaseResult()
            .withMethod(method("paramTest"))
            .withStatus(FAILED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes());
    }

    private TestCaseResult aFailedTestMethod() {
        return aTestCaseResult()
            .withMethod(method("testMethod"))
            .withName("testMethod")
            .withWordify("Failing assertion")
            .withStatus(FAILED)
            .withTestSuiteClass(testSuiteClass())
            .withNotes(new Notes())
            .build();
    }
}
