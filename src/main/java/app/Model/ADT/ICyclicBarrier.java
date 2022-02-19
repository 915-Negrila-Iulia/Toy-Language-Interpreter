package app.Model.ADT;

import java.util.Map;

public interface ICyclicBarrier<K,V> {

    void add(K key, V value);
    V lookUp(K key);
    void update(K key, V value);
    boolean contains(K key);
    Iterable<K> keySet();
    Map<K,V> getContent();
}