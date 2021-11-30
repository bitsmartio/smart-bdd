/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2021  James Bayliss
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package component.report;

import io.bitsmart.bdd.report.junit5.launcher.TestLauncher;
import io.bitsmart.bdd.report.junit5.results.extension.ReportExtension;
import io.bitsmart.bdd.report.report.adapter.ReportFactory;
import io.bitsmart.bdd.report.report.model.Report;
import io.bitsmart.bdd.report.report.model.TestSuite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.undertest.basic.ClassUnderTest;

import java.io.IOException;

import static component.report.ReportAssertions.assertPassingTestSuite;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example output from JUnit
 *
 * <?xml version="1.0" encoding="UTF-8"?>
 * <testsuite name="junit5.learning.parameters.LearningTest" tests="8" skipped="0" failures="0" errors="0" timestamp="2021-03-30T20:03:44" hostname="Jamess-MacBook-Pro.local" time="0.021">
 *   <properties/>
 *   <testcase name="[1] test, TEST" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <testcase name="[2] tEst, TEST" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <testcase name="[3] Java, JAVA" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <testcase name="RepeatingTest 1/5" classname="junit5.learning.parameters.LearningTest" time="0.005"/>
 *   <testcase name="RepeatingTest 2/5" classname="junit5.learning.parameters.LearningTest" time="0.0"/>
 *   <testcase name="RepeatingTest 3/5" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <testcase name="RepeatingTest 4/5" classname="junit5.learning.parameters.LearningTest" time="0.0"/>
 *   <testcase name="RepeatingTest 5/5" classname="junit5.learning.parameters.LearningTest" time="0.001"/>
 *   <system-out><![CDATA[RepeatingTest 1/5-->1
 * RepeatingTest 2/5-->2
 * RepeatingTest 3/5-->3
 * RepeatingTest 4/5-->4
 * RepeatingTest 5/5-->5
 * ]]></system-out>
 *   <system-err><![CDATA[]]></system-err>
 * </testsuite>
 */
public class ReportForPassingTestSuiteTest {
    private static final Class<?> PASSING_CLASS_UNDER_TEST = ClassUnderTest.class;

    @BeforeEach
    void setUp() {
        ReportExtension.getTestContext().reset();
    }

    @Test
    void reportForOneClassGeneratedCorrectly() {
        TestLauncher.launch(PASSING_CLASS_UNDER_TEST);
        Report report = ReportFactory.create(ReportExtension.getTestContext().getTestResults());
       
        assertReport(report);
    }

    public static void assertReport(Report report) {
        assertThat(report).isNotNull();
        assertThat(report.getTestCases()).hasSize(6);
        assertThat(report.getTestSuites()).hasSize(1);

        TestSuite testSuite = report.getTestSuites().get(0);
        assertPassingTestSuite(testSuite);
    }
}
