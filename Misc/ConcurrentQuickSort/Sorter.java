
public interface Sorter extends AutoCloseable {
  <T extends Comparable<T>> void sort(T[] arr);
}
