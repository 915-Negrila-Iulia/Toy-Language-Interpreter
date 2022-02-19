package app.Model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LatchTable<K,V> implements ILatchTable<K,V>{

    private final Map<K,V> dictionary;

    public LatchTable(){
        dictionary = new HashMap<K,V>();
    }

    public void add(K key, V value){dictionary.put(key,value);}
    public void update(K key, V value){dictionary.put(key,value);}
    public V lookUp(K key){return dictionary.get(key);}
    public Collection<V> values(){return dictionary.values();};
    public Collection<K> keys(){return dictionary.keySet();};
    public void remove(K key){dictionary.remove(key);};
    public boolean contains(K key){return dictionary.containsKey(key);}
    public ILatchTable<K, V> deepCopy(){
        ILatchTable<K,V> newDictionary = new LatchTable<>();
        for(K key: this.dictionary.keySet())
            newDictionary.add(key,this.dictionary.get(key));
        return newDictionary;
    };
    public Map<K, V> getContent(){return this.dictionary;}
    public String toString(){
        return "latchTable: " + dictionary.toString();
    };

}
