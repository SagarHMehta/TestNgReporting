package com.java.reporting.TestNgReporting.customReporters;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.opencsv.CSVWriter;

public class CSVReporter implements IReporter {

	private static String path = System.getProperty("user.dir") + File.separator + "results.csv";

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

		for (ISuite suite : suites) {

			Map<String, ISuiteResult> suiteResults = suite.getResults();

			List<String[]> listOfResults = new ArrayList<String[]>();

			listOfResults.add(new String[] { "Test Name", "Test Duration", "Test Status" });

			for (ISuiteResult sr : suiteResults.values()) {
				Set<ITestResult> skippedResults = sr.getTestContext().getSkippedTests().getAllResults();
				for (ITestResult result : skippedResults) {
					listOfResults.add(new String[] { result.getName(),
							String.valueOf(result.getEndMillis() - result.getStartMillis()), "SKIPPED" });
				}

				Set<ITestResult> failedResults = sr.getTestContext().getFailedTests().getAllResults();
				for (ITestResult result : failedResults) {
					listOfResults.add(new String[] { result.getName(),
							String.valueOf(result.getEndMillis() - result.getStartMillis()), "FAILED" });
				}

				Set<ITestResult> passedResults = sr.getTestContext().getPassedTests().getAllResults();
				for (ITestResult result : passedResults) {
					listOfResults.add(new String[] { result.getName(),
							String.valueOf(result.getEndMillis() - result.getStartMillis()), "Passed" });
				}

			}

			try {
				csvWriterAll(listOfResults, path);
			} catch (Exception e) {
				e.printStackTrace();
				new RuntimeException("CSV Report writing failed with exception:" + e.getStackTrace());
			}

		}
	}

	public void csvWriterAll(List<String[]> stringArray, String path) throws Exception {
		CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
		writer.writeAll(stringArray);
		writer.close();
	}
}
