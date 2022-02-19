package app.Model.ToyType;

import app.Model.ToyValue.Value;

public interface Type {
    /*
        Interface 'Type'
        Type objects describe the types of parameters used in the interpreter
        ( int, bool, string, reference )

        Abstract methods:
        - defaultValue -> returns a Value object with a default value depending on type
        - equals -> returns true if an object given as input parameter is an instance
                    of a certain Type class, false otherwise
        - toString -> returns a string representing the name of the type
        - deepCopy -> returns a copy of the current Type object
     */

    Value defaultValue();
    boolean equals(Object object);
    String toString();
    Type deepCopy();
}
