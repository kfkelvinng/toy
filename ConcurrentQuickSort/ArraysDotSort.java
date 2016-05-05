import java.util.Arrays;

public class ArraysDotSort implements Sorter {
	
	public <T extends Comparable<T>> void sort(T[] arr) {
		Arrays.sort(arr);
	}

	@Override
	public void close() throws Exception {

	}
  
}

