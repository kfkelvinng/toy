import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MatrixMultiplication {

  static final int T = 6;
  static final int TILE = 1<<T; // 16
  static final int N2 = 9;
  static final int N = 1<<N2;
  
  static final int[] c = new int[(N<<N2)+N];
  static final int[] a = new int[(N<<N2)+N];
  static final int[] b = a;
  
  static final ExecutorService es = Executors.newFixedThreadPool(4);
  
  public static void main(String[] args) {
    
    System.gc();
    long begin = System.currentTimeMillis();
    
    for (int i = 0; i < 100; i++) {
      vanilla();
    }
    
    long end = System.currentTimeMillis();
    System.out.println((end-begin)/100);
    es.shutdown();

  }

  private static void vanilla() {
    for (int i=0; i<N; i++) {
      for (int j=0; j<N; j++) {
        for (int k=0; k<N; k++)
          c[(i<<N2)+j] += a[(i<<N2)+k]*b[(j<<N2)+k];
      }
    }
  }

  private static void tile() {
  }
  
  private static void tileThreaded() {
    
    ArrayList<Callable<Object>> runner = new ArrayList<Callable<Object>>(N/TILE);
    final AtomicInteger spinlockCountDownLatch = new AtomicInteger(0);
    for (int I=0; I<N; I+=TILE) {
      final int i = I;
      spinlockCountDownLatch.incrementAndGet();
      runner.add(Executors.callable(new Runnable(){
      public void run() {
                for ( int j=0; j<N; j+=TILE )
                    for ( int k=0; k<N; k+=TILE )
                    /* Regular multiply inside the tiles */
                        for ( int y=i; y<i+TILE; y++ )
                            for ( int x=j; x<j+TILE; x++ )
                                for ( int z=k; z<k+TILE; z++ )
                                    c[(y<<N2)+x] += a[(y<<N2)+z]*b[(x<<N2)+z];
                spinlockCountDownLatch.decrementAndGet();
      }
    }));
    }
    
    try {
      es.invokeAll(runner);
    } catch (Exception e) {}
    while (spinlockCountDownLatch.get()!=0);
  }

}

