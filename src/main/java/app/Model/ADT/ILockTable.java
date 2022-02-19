package app.Model.ADT;

import java.util.Collection;
import java.util.Map;

public interface ILockTable<K,V> {
    void add(K key, V value);
    void update(K key, V value);
    boolean contains(K key);
    V getValue(K key);
    Iterable<K> keySet();
    Collection<V> values();
    ILockTable<K,V> deepCopy();
    Map<K,V> getContent();
    String toString();
    int size();
}