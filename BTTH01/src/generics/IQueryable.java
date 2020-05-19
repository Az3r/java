package generics;

public interface IQueryable<T extends Comparable<T>> {
    T getMax();
}
