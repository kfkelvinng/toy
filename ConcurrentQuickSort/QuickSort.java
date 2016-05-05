public class QuickSort implements Sorter {
	
	public <T extends Comparable<T>> void sort(T[] arr) {
		quickSortSequential(arr, 0, arr.length-1);
	}
	
  static <T extends Comparable<T>> void quickSortSequential(T[] arr, int left, int right) {
    if (right > left) {
      int pivot = partition(arr, left, right);
      quickSortSequential(arr, left, pivot - 1);
      quickSortSequential(arr, pivot + 1, right);
    }
  }
  
  static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
    int pivotIndex = (low + high) / 2;
    T pivotValue = arr[pivotIndex];
    swap(arr, low, pivotIndex); // Move pivot to end
    int left = low;
    for (int i = low + 1; i <= high; i++) {
      if (arr[i].compareTo(pivotValue) < 0) {
        left++;
        swap(arr,i, left);
      }
    }
    swap(arr, low, left); // Move pivot to its final place
    return left;
  }
  
  
  static <T> void swap(T[] arr, int i, int j) {
    T temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

	@Override
	public void close() throws Exception {

	}
  
}

