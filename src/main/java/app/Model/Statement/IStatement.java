package app.Model.Statement;

import app.Model.ADT.MyDictionary;
import app.Model.ProgramState;
import app.Model.ToyType.Type;

public interface IStatement {

        /*
        Interface 'IStatement'
        IStatement objects describe the statements used in the interpreter;
        modifies data structures

        Abstract methods:
        - execute -> returns the state of the program
        - typecheck -> returns the TypeEnvironment after performing a statement
        - toString -> returns a string representing the statement
        - deepCopy -> returns a copy of the current IStatement object
     */

    ProgramState execute (ProgramState state) throws Exception;
    MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception;
    String toString();
    IStatement deepCopy();
}
