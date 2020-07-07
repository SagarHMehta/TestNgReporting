package com.java.reporting.TestNgReporting.Functions;

public class ArrayElementsSorting {
	/**
	 * <p>
	 * Function to sort only Positive Integer Array Elements, Negative elements will
	 * be as is in Array. Array will be sorted in place/memory.
	 * </p>
	 * 
	 * @param arr - Array to be Sorted.
	 */
	public static void sortPositiveIntegerElements(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				int temp = arr[j];
				if (temp >= 0) {
					int k = j + 1;
					while (arr[k] < 0)
						k++;
					if (arr[j] > arr[k] && arr[k] >= 0) {
						arr[j] = arr[k];
						arr[k] = temp;
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * Function to sort all Elements of Integer Array with out using extra space.
	 * </p>
	 * 
	 * @param arr
	 */
	public static void sortAllIntegerElements(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				int temp = arr[j];
				if (temp >= 0) {
					int k = j + 1;
					if (arr[j] > arr[k] && arr[k] >= 0) {
						arr[j] = arr[k];
						arr[k] = temp;
					}
				}
			}
		}
	}
}
