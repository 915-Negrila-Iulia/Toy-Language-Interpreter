package app.Model.ADT;

import java.util.Collection;
import java.util.Map;

public interface ILatchTable<K,V> {

    void add(K key, V value);
    void update(K key, V value);
    V lookUp(K key) ;
    Collection<V> values();
    Collection<K> keys();
    void remove(K fd);
    boolean contains(K key);
    ILatchTable<K, V> deepCopy();
    Map<K, V> getContent();
}
