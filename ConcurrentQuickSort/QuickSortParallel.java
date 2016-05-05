import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;


public class QuickSortParallel implements Sorter {
	
	private static final int nThreads = 4;
	private static final int minSize = 1<<15;
  
  ExecutorService es = Executors.newFixedThreadPool(nThreads);
  
  Semaphore s = new Semaphore(nThreads);
  
  ConcurrentLinkedQueue<Future<?>> running = new ConcurrentLinkedQueue<Future<?>>();
  
	public <T extends Comparable<T>> void sort(T[] arr) {
		quickSortParallel(arr, 0, arr.length-1);
	}
  
  <T extends Comparable<T>> void quickSortParallel(final T[] arr, final int left, final int right) {
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
  
  <T extends Comparable<T>> void quickSortParallelHelper(final T[] arr, final int left, final int right) {
    
    if (right > left) {
      final int pivot = QuickSort.partition(arr, left, right);
      
      // LEFT
      if ((pivot-1)-left<minSize) {
      	quickSortParallelHelper(arr, left, pivot - 1);
      } else {
        Runnable r1 = new Runnable(){
          @Override
          public void run() {
            quickSortParallelHelper(arr, left, pivot - 1);
          }
        };
        running.offer(es.submit(r1));
      }
      
      // RIGHT
      if (right-(pivot-1)<minSize) {
      	quickSortParallelHelper(arr, pivot + 1, right);
      } else {
        Runnable r2 = new Runnable() {
          @Override
          public void run() {
            quickSortParallelHelper(arr, pivot + 1, right);
          }
        };
        running.offer(es.submit(r2));        
      }
    }
    
  }

	@Override
	public void close() throws Exception {
	  es.shutdown();
	}
  
}

