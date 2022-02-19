package app.Model.ToyValue;

import app.Model.ToyType.Type;

public interface Value {
    /*
        Interface 'Value'
        Value objects describe the values of parameters used in the interpreter

        Abstract methods:
        - getType -> returns the Type of the Value object
        - equals -> returns true if an object given as input parameter is an instance
                    of a certain Value class and its value is equal to the Value's value,
                    false otherwise
        - toString -> returns a string representing the value of Value object
        - deepCopy -> returns a copy of the current Value object
     */

    Type getType();
    boolean equals(Object object);
    String toString();
    Value deepCopy();
}
