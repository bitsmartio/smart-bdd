package junit5.parsing;

import com.thoughtworks.qdox.model.JavaMethod;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.TestTag;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import source.JavaSourceWrapper;
import wordify.WordifyClass;
import wordify.WordifyString;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public class ExecutionListener implements TestExecutionListener {
    private static final Logger logger = Logger.getLogger(ExecutionListener.class.getName());

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testExecutionResult.getStatus() == TestExecutionResult.Status.FAILED) {
            logger.warning("FAILED >>>> " + testIdentifier);
        } else {
            // now get the contents of the method
            logger.info("SUCCESS >>>> " + testIdentifier);
            wordify(testIdentifier);
        }
    }

    private void wordify(TestIdentifier testIdentifier) {
        testIdentifier.getSource().ifPresent(this::wordify);
    }

    private void wordify(TestSource source) {
        MethodSource methodSource = null;
        if (source instanceof MethodSource) {
            methodSource = (MethodSource) source;
        } else {
            logger.info(">>>> testSource not from method:" + source);
            return;
        }

        //final Method javaMethod = methodSource.getJavaMethod();
        final String wordify = new WordifyClass().wordify(methodSource.getJavaClass(),  methodSource.getMethodName());
        logger.info(">>>> wordify: " + wordify);
    }

    private void debugFields(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        final String displayName = testIdentifier.getDisplayName();
        final String legacyReportingName = testIdentifier.getLegacyReportingName();
        final boolean test = testIdentifier.isTest();
        final Optional<String> parentId = testIdentifier.getParentId();
        final Optional<TestSource> source = testIdentifier.getSource();
        final Set<TestTag> tags = testIdentifier.getTags();
        final TestDescriptor.Type type = testIdentifier.getType();
        final String uniqueId = testIdentifier.getUniqueId();
        final TestExecutionResult.Status status = testExecutionResult.getStatus();
    }

}
