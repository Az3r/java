package generics;

import java.util.ArrayList;
import java.util.Collection;

public final class QueryableCollection<T extends Comparable<T>> extends ArrayList<T> implements IQueryable<T> {

    public QueryableCollection(Collection<? extends T> c) {
        super(c);
    }

    public QueryableCollection(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public T getMax() {
        if (size() == 0) return null;
        T max = get(0);
        for (int i = 1; i < size(); ++i) {
            max = max.compareTo(get(i)) < 0 ? get(i) : max;
        }
        return max;
    }
}
