package app.Model.ADT;

import app.Model.Exception.VariableNotDefinedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1,T2> implements IDictionary<T1,T2>{

    private final Map<T1, T2> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<T1, T2>();
    }

    @Override
    public T2 lookUp (T1 id) throws VariableNotDefinedException {
        if(isDefined(id)){
            return dictionary.get(id);
        }
        else{
            throw new VariableNotDefinedException(id+" variable is not defined");
        }
    };

    @Override
    public void add(T1 v1, T2 v2){
        dictionary.put(v1, v2);
    };

    @Override
    public void update(T1 v1, T2 v2){
        dictionary.replace(v1,v2);
    };

    @Override
    public boolean isDefined(T1 id){
        return dictionary.containsKey(id);
    };

    @Override
    public String toString(){
        return "dictionary: " + dictionary.toString();
    };

    public Collection<T2> values(){
        return dictionary.values();
    };

    public int size(){return dictionary.size();}

    public void remove(T1 v1){
        dictionary.remove(v1);
    }

    public Set<T1> keySet(){return dictionary.keySet();}

    @Override
    public IDictionary<T1,T2> deepCopy(){
        IDictionary<T1,T2> newDictionary = new MyDictionary<>();
        for(T1 key: this.dictionary.keySet())
            newDictionary.add(key,this.dictionary.get(key));
        return newDictionary;
    };

    public Map<T1,T2> getContent(){
        return this.dictionary;
    };
}
