package app.Model.ADT;

public class Pair<K,V> {
    private K first;
    private V second;

    public Pair(K first, V second){
        this.first = first;
        this.second = second;
    }

    public K getFirst(){return this.first;}
    public V getSecond(){return this.second;}

    public void setFirst(K f){this.first = f;}
    public void setSecond(V s){this.second = s;}

    public String toString(){
        return "("+first.toString()+","+second.toString()+")";
    }

}