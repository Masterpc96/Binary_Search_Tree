import java.util.Comparator;

/**
 * Created by Michał on 03.05.2017.
 */
public class MyComparator<T> implements Comparator<T> {

    @Override
    public int compare(T left, T right) {
        return ((Comparable<T>)left).compareTo(right);
    }
}
