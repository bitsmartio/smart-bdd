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

package io.bitsmart.bdd.ft.report.infrastructure.utils;

import java.io.File;
import java.io.IOException;

import static java.lang.System.getProperty;

public class HtmlReportTestUtils {
    public static String loadTestSuite(Class<?> clazz) throws IOException {
        return new FileLoader().toString(testSuiteFile(clazz));
    }

    public static String loadReportIndex() throws IOException {
        return new FileLoader().toString(homePageFile());
    }

    public static File homePageFile() {
        return new File(outputDirectory(), "index.html");
    }

    public static File testSuiteFile(Class<?> clazz) {
        return new File(outputDirectory(), "TEST-" + clazz.getCanonicalName() + ".html");
    }

    public static File outputDirectory() {
        return new File(getProperty("java.io.tmpdir") +  "io.bitsmart.bdd.report/report/");
    }
}
