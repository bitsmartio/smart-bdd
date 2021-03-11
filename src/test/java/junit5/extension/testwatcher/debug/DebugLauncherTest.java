package junit5.extension.testwatcher.debug;

import junit5.extension.testwatcher.results.ResultsExtension;
import junit5.extension.utils.TestLauncher;
import org.junit.jupiter.api.Test;
import results.junit.testcapture.CaptureTestClass;
import results.junit.testcapture.CaptureTestMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DebugLauncherTest {

    @Test
    void launchTests() {
        ResultsExtension.reset();
        new TestLauncher().launch(new junit5.extension.utils.TestListener(), ClassUnderTest.class);

        assertThat(DebugExtension.getCapturedTestClasses().getClasses()).containsExactly("ClassUnderTest");
        CaptureTestClass capturedTestClass = DebugExtension.getCapturedTestClasses().getCapturedClasses().get("ClassUnderTest");
        assertThat(capturedTestClass).isNotNull();
        assertThat(capturedTestClass.getCapturedMethodsForClass().getCapturedMethodNames()).containsExactly(
            "beforeAll",
            "interceptBeforeAllMethod",
            "interceptTestClassConstructor", // firstTest
            "interceptTestClassConstructor", // secondTest
            "interceptTestClassConstructor", // thirdParamTest param 1
            "interceptTestClassConstructor", // thirdParamTest param 2
            "interceptTestClassConstructor", // thirdParamTest param 3
            "interceptAfterAllMethod",
            "afterAll"
        );

        assertThat(capturedTestClass.getMethodNames()).contains(
            "firstTest",
            "secondTest",
            "thirdParamTest"
        );

        CaptureTestMethod capturedFirstTestMethod = capturedTestClass.getCapturedTestMethod("firstTest");
        assertThat(capturedFirstTestMethod.getCapturedMethodNames()).containsExactly(
            "beforeEach",
            "interceptBeforeEachMethod",
            "interceptTestMethod",
            "interceptAfterEachMethod",
            "afterEach");

        CaptureTestMethod capturedSecondTestMethod = capturedTestClass.getCapturedTestMethod("firstTest");
        assertThat(capturedSecondTestMethod.getCapturedMethodNames()).containsExactly(
            "beforeEach",
            "interceptBeforeEachMethod",
            "interceptTestMethod",
            "interceptAfterEachMethod",
            "afterEach");

        List<CaptureTestMethod> capturedThirdParamTestMethods = capturedTestClass.getCapturedTestMethods("thirdParamTest");
        capturedThirdParamTestMethods.forEach(captureTestMethod -> {
            assertThat(captureTestMethod.getCapturedMethodNames()).containsExactly(
                "beforeEach",
                "interceptBeforeEachMethod",
                "interceptTestTemplateMethod", // notice that this is interceptTestTemplateMethod
                "interceptAfterEachMethod",
                "afterEach");
        });

    }
}
