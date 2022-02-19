package app.Model.ADT;

import java.util.HashMap;
import java.util.Map;

public class CyclicBarrier<K,V> implements ICyclicBarrier<K,V>{

    private final Map<K,V> dictionary;

    public CyclicBarrier()
    {
        dictionary = new HashMap<K,V>();
    }

    @Override
    public void add(K key,V value) {
        dictionary.put(key,value);
    }

    @Override
    public V lookUp(K key) {
        return dictionary.get(key);
    }

    @Override
    public void update(K key, V value) {
        dictionary.put(key,value);
    }

    @Override
    public boolean contains(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public Iterable<K> keySet() {
        return dictionary.keySet();
    }

    public Map<K,V> getContent(){
        return this.dictionary;
    };
}