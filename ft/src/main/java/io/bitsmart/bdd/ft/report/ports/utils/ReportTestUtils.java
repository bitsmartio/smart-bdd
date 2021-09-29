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

package io.bitsmart.bdd.ft.report.ports.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bitsmart.bdd.ft.report.ports.json.model.ReportIndex;
import io.bitsmart.bdd.ft.report.ports.json.model.TestSuite;

import java.io.File;
import java.io.IOException;

import static java.lang.System.getProperty;

public class ReportTestUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static TestSuite loadTestSuite(Class<?> clazz) throws IOException {
        String contents = new FileLoader().toString(testSuiteFile(clazz));
        return MAPPER.readValue(contents, TestSuite.class);
    }

    public static ReportIndex loadReportIndex() throws IOException {
        String contents = new FileLoader().toString(homePageFile());
        return MAPPER.readValue(contents, ReportIndex.class);
    }

    public static File homePageFile() {
        return new File(outputDirectory(), "index.json");
    }

    public static File testSuiteFile(Class<?> clazz) {
        return new File(outputDirectory(), "TEST-" + clazz.getName() + ".json");
    }

    public static File outputDirectory() {
        return new File(getProperty("java.io.tmpdir") +  "io.bitsmart.bdd.report/data/");
    }
}
