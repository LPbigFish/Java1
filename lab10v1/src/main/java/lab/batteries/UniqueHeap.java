package lab.batteries;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class UniqueHeap<T> {

    Set<T> set;

    int count;

    UniqueHeap() {
        set = new HashSet<>();
        count = 0;
    }

    public void add(T t) {
        set.add(t);
        count++;
    }

    public int getCount() {
        return count;
    }

    public Set<T> grabAll() {
        Set<T> result = new HashSet<>(this.set);
        set.clear();
        count = 0;
        return result;
    }
}
