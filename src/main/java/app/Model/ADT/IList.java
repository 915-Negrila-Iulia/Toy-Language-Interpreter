package app.Model.ADT;

public interface IList<T> {

    void add(T v);
    T get(int index);
    boolean isEmpty();
    int size();
    String toString();
}
