package app.Model.ADT;

import java.util.Iterator;

public interface IStack<T>{

    T pop();
    T top();
    void push(T v);
    boolean isEmpty();
    int size();
    String toString();
    Iterator<T> iterator();
    IStack<T> deepCopy();
}
