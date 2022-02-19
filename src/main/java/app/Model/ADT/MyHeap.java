package app.Model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<V> implements IHeap<V>{

    private Map<Integer, V> heap;
    private int addressOfLastAdded;
    private int freeLocation;

    public MyHeap(){
        this.heap = new HashMap<Integer,V>();
        this.addressOfLastAdded = 0;
        this.freeLocation = 1;
    }

    public void add(V val){
        heap.put(freeLocation,val);
        addressOfLastAdded++;
        freeLocation++;
    };

    public void update(int address, V val){
        heap.replace(address,val);
    };

    public void remove(int address){
        heap.remove(address);
    };

    public V lookUp(int address) throws Exception{
        if(isDefined(address)){
            return heap.get(address);
        }
        else
            throw new Exception("address "+address+" does not exist");
    };

    public boolean isDefined(int address){
        return heap.containsKey(address);
    };

    public int getFreeLocation(){
        return this.freeLocation;
    };

    public int getLocationOfLastAdded(){
        return this.addressOfLastAdded;
    };

    public Collection<V> getValues(){return heap.values();};

    public Set<Integer> getKeys(){return heap.keySet();}

    public void setContent(Map<Integer,V> newHeap){
        this.heap = newHeap;
    };

    public Map<Integer,V> getContent(){
        return this.heap;
    };

    public String toString(){
        return "heap: " + heap.toString();
    };

    public int size(){
        return heap.size();
    };
}
