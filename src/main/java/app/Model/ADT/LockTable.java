package app.Model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LockTable<K,V> implements ILockTable<K,V>{

    private final Map<K,V> dictionary;

    public LockTable(){
        dictionary = new HashMap<K, V>();
    }

    @Override
    public V getValue (K id) {return dictionary.get(id);};

    @Override
    public void add(K key, V value){
        dictionary.put(key, value);
    };

    @Override
    public void update(K key, V value){
        dictionary.replace(key,value);
    };

    @Override
    public boolean contains(K id){
        return dictionary.containsKey(id);
    };

    @Override
    public String toString(){
        return "lockTable: " + dictionary.toString();
    };

    public Collection<V> values(){
        return dictionary.values();
    };

    public int size(){return dictionary.size();}

    public Iterable<K> keySet(){return dictionary.keySet();}

    @Override
    public ILockTable<K,V> deepCopy(){
        ILockTable<K,V> newDictionary = new LockTable<>();
        for(K key: this.dictionary.keySet())
            newDictionary.add(key,this.dictionary.get(key));
        return newDictionary;
    };

    public Map<K,V> getContent(){
        return this.dictionary;
    };
}
