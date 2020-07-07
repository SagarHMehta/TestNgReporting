package com.java.reporting.TestNgReporting.testcases;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.java.reporting.TestNgReporting.Functions.ArrayElementsSorting;

public class PositiveSortingCheck {

	@Test(description = "Test Functionality to test sorting of Positive Integers Only.")
	public void testPositiveSortingOfIntegers() {
		int[] arr = { -1, 3, 4, 2, -2, 4 };
		ArrayElementsSorting.sortPositiveIntegerElements(arr);

		int[] arrExpected = { -1, 2, 3, 4, -2, 4 };
		Assert.assertTrue(Arrays.equals(arr, arrExpected), "Only Positive Integer Elements are not sorted.");
	}

	@Test(description = "Skipped test case.")
	public void skippedTestCase() {
		throw new SkipException("Test Case will be skipped.");
	}

	@Test(description = "Failing Test Assertions.")
	public void failedTestCase() {
		int[] arr = { -1, 3, 4, 2, -2, 4 };
		ArrayElementsSorting.sortAllIntegerElements(arr);
		int[] arrExpected = { -1, 2, 3, 4, -2, 4 };
		Assert.assertTrue(arr.equals(arrExpected), "All Integer elements are not sorted properly.");
	}

}
