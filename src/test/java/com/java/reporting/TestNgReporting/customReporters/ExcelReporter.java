package com.java.reporting.TestNgReporting.customReporters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class ExcelReporter implements IReporter {

	private static String path = System.getProperty("user.dir") + File.separator + "results.xlsx";

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		for (ISuite suite : suites) {
			XSSFSheet sheet = workbook.createSheet(suite.getName());
			Map<String, ISuiteResult> suiteResults = suite.getResults();

			List<Object[]> listOfResults = new ArrayList<Object[]>();

			listOfResults.add(new String[] { "Test Name", "Test Duration", "Test Status" });

			for (ISuiteResult sr : suiteResults.values()) {
				Set<ITestResult> skippedResults = sr.getTestContext().getSkippedTests().getAllResults();
				for (ITestResult result : skippedResults) {
					listOfResults.add(new Object[] { result.getName(),
							String.valueOf(result.getEndMillis() - result.getStartMillis()), "SKIPPED" });
				}

				Set<ITestResult> failedResults = sr.getTestContext().getFailedTests().getAllResults();
				for (ITestResult result : failedResults) {
					listOfResults.add(new Object[] { result.getName(),
							String.valueOf(result.getEndMillis() - result.getStartMillis()), "FAILED" });
				}

				Set<ITestResult> passedResults = sr.getTestContext().getPassedTests().getAllResults();
				for (ITestResult result : passedResults) {
					listOfResults.add(new Object[] { result.getName(),
							String.valueOf(result.getEndMillis() - result.getStartMillis()), "PASSED" });
				}

			}

			try {
				excelWriterAll(listOfResults, sheet);
			} catch (Exception e) {
				e.printStackTrace();
				new RuntimeException("Excel Report writing failed with exception:" + e.getStackTrace());
			}

		}

		try {
			FileOutputStream opStream = new FileOutputStream(new File(path));
			workbook.write(opStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			new RuntimeException("Excel Report writing failed as File Not Found:" + e.getStackTrace());
		} catch (IOException e) {
			e.printStackTrace();
			new RuntimeException("Excel Report writing failed with IO Exception:" + e.getStackTrace());
		}
	}

	public void excelWriterAll(List<Object[]> objArrayList, XSSFSheet sheet) throws Exception {
		int rowNum = 0;

		for (Object[] objArr : objArrayList) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : objArr) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				} else if (field instanceof Date) {
					cell.setCellValue((Date) field);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
				} else {
					throw new RuntimeException("Attribute Type not compatiblle to be written on excel report" + field);
				}
			}
		}
	}
}
