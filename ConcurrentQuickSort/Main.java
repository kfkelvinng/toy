import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class Main {

  private static final int BENCHMARK_NUM = 16;
  private static final int N = 1<<20;
  
  public static void main(String[] args) throws Exception {
  	
  	try (Sorter sorter = new ArraysDotSort()) {
  		benchmark(sorter, "Arrays.sort");
  	}
  	
  	try (Sorter sorter = new QuickSort()) {
  		benchmark(sorter, "QuickSort");
  	}
  	
  	try (Sorter sorter = new QuickSortParallel()) {
  		benchmark(sorter, "QuickSortParallel");
  	}
  	
  }
  
  private static void benchmark(Sorter sorter, String name) {
    long total = 0;
    
    Integer[] arr = new Integer[N];
    for (int i = 0; i < arr.length; i++) {
	    arr[i] = i;
	  }
    
    for (int benchmark = 0; benchmark < BENCHMARK_NUM; benchmark++) {
  	  Collections.shuffle(Arrays.asList(arr), new Random(benchmark));
      long start = System.currentTimeMillis();
      sorter.sort(arr);
      long stop = System.currentTimeMillis();
      total += stop - start;
      for (int i = 0; i< arr.length; i++) {
      	if (arr[i] != i) {
      		throw new IllegalStateException("Verification failed");
      	}
      }
    }
    System.out.println(name+":"+total);
  }
  
}
