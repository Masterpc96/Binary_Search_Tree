/**
 * Created by Michał on 03.05.2017.
 */
public class Node<T>{
    T key;
    Node<T> left = null;
    Node<T> right = null;
    Node<T> parent = null;
    int nodes=0;
    int height=0;
    int leaves=0;
    int overload=0;

    Node(T key) {
        this.key = key;
    }
}
