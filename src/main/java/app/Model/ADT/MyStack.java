package app.Model.ADT;

import java.util.Iterator;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {

    private final Stack<T> myStack;

    public MyStack(){
        myStack = new Stack<>();
    }

    @Override
    public T pop() {
        return myStack.pop();
    }

    @Override
    public T top(){return myStack.firstElement();}

    @Override
    public void push(T v) {
        myStack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return myStack.isEmpty();
    }

    @Override
    public int size(){
        return myStack.size();
    }

    @Override
    public String toString(){
        return "stack: " + myStack.toString();
    }

    @Override
    public Iterator<T> iterator(){
        return myStack.iterator();
    }

    @Override
    public IStack<T> deepCopy(){
        IStack<T> newStack = new MyStack<>();
        Iterator<T> iterator = this.myStack.iterator();
        while(iterator.hasNext()){
            T elem = iterator.next();
            newStack.push(elem);
        }
        return newStack;
    };
}
