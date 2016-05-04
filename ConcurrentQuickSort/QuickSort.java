import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class QuickSort {
  
  private static ExecutorService es = Executors.newFixedThreadPool(2);
  
  private static final int BENCHMARK_NUM = 100;
  
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    
    long total = 0;
    
    for (int benchmark = 0; benchmark < BENCHMARK_NUM; benchmark++) {
      Integer[] arr = new Integer[1000000];
      for (int i = 0; i < arr.length; i++) {
        arr[i] = (int)(Math.random()*Integer.MAX_VALUE);
      }
      
      long start = System.currentTimeMillis();
//      quickSortParallel(arr, 0, arr.length-1);
      quickSortSequential(arr, 0, arr.length-1);
      long stop = System.currentTimeMillis();
      total += stop - start;
      System.out.println(stop-start);
    }
    
    System.out.println(total);
    
    es.shutdown();
  }
  
  
  private static <T extends Comparable<T>> void quickSortSequential(T[] arr, int left, int right) 
  {
      if (right > left)
      {
          int pivot = partition(arr, left, right);
          quickSortSequential(arr, left, pivot - 1);
          quickSortSequential(arr, pivot + 1, right);
      }
  }
  
  public static ConcurrentLinkedQueue<Future<?>> running = new ConcurrentLinkedQueue<Future<?>>();
  
  public static <T extends Comparable<T>> void quickSortParallel(final T[] arr, final int left, final int right) {
    quickSortParallelHelper(arr, left, right);
    for(Future<?> f; (f = running.poll()) != null;) {
      try {
        f.get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }
  }
  
  public static <T extends Comparable<T>> void quickSortParallelHelper(final T[] arr, final int left, final int right) {
    
    if (right > left)
    {
      final int pivot = partition(arr, left, right);
      if (right - left > 10000) {
        quickSortSequential(arr, left, right);
      } else {
        Runnable r1 = new Runnable(){
          @Override
          public void run() {
            quickSortParallelHelper(arr, left, pivot - 1);
          }
        };
        Runnable r2 = new Runnable() {
          @Override
          public void run() {
            quickSortParallelHelper(arr, pivot + 1, right);
          }
        };
        
        // This Future blocks until all the children Future is offered to the queue
        running.offer(es.submit(r1));
        running.offer(es.submit(r2));        
      }
    }
    
  }

  
  private static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
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
  
  
  private static <T> void swap(T[] arr, int i, int j) {
    T temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
  
}

