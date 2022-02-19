package app.Model.ADT;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IHeap <V>{

    void add(V val);
    void update(int address, V val);
    void remove(int address);
    V lookUp(int address) throws Exception;
    boolean isDefined(int address);
    int getFreeLocation();
    int getLocationOfLastAdded();
    String toString();
    Collection<V> getValues();
    Set<Integer> getKeys();
    void setContent(Map<Integer,V> newHeap);
    Map<Integer,V> getContent();
    int size();


}
