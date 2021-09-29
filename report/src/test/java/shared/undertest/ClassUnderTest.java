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

package shared.undertest;

import io.bitsmart.bdd.report.junit5.results.extension.ReportExtension;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <?xml version="1.0" encoding="UTF-8"?>
 * <testsuite name="junit5.results.undertest.ClassUnderTest" tests="4" skipped="0" failures="0" errors="0" timestamp="2021-04-12T20:09:43" hostname="Jamess-MacBook-Pro.local" time="0.011">
 *   <properties/>
 *   <testcase name="testMethod()" classname="junit5.results.undertest.ClassUnderTest" time="0.002"/>
 *   <testcase name="[1] value 1" classname="junit5.results.undertest.ClassUnderTest" time="0.002"/>
 *   <testcase name="[2] value 2" classname="junit5.results.undertest.ClassUnderTest" time="0.002"/>
 *   <testcase name="[3] value 3" classname="junit5.results.undertest.ClassUnderTest" time="0.002"/>
 *   <system-out><![CDATA[]]></system-out>
 *   <system-err><![CDATA[]]></system-err>
 * </testsuite>
 */
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(ReportExtension.class)
public class ClassUnderTest {

    @Order(0)
    @Test
    void testMethod() {
        passingAssertion();
    }

    @ParameterizedTest
    @Order(1)
    @ValueSource(strings = { "value 1", "value 2", "value 3" })
    void paramTest(String param) {
        passingAssertionWith(param);
    }

    private void passingAssertion() {
        assertThat(true).isTrue();
    }

    private void passingAssertionWith(String param) {
        assertThat(param).isNotNull();
    }
}