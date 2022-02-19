package app.Model.ADT;

import java.util.ArrayList;

public class MyList<T> implements IList<T> {

    private final ArrayList<T> list;

    public MyList(){
        list = new ArrayList<>();
    }

    @Override
    public void add(T v){
        list.add(v);
    }

    @Override
    public T get(int index){
        return list.get(index);
    }

    @Override
    public boolean isEmpty(){
        return list.isEmpty();
    }

    @Override
    public int size(){
        return list.size();
    }

    @Override
    public String toString(){
        return "list: " + list.toString();
    }
}
