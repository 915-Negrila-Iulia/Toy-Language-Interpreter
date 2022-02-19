package app.Model.ADT;


import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<T1,T2>{

    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    T2 lookUp(T1 id) throws Exception;
    boolean isDefined(T1 id);
    String toString();
    Collection<T2> values();
    int size();
    void remove(T1 v1);
    Map<T1,T2> getContent();
    Set<T1> keySet();
    IDictionary<T1,T2> deepCopy();
}
