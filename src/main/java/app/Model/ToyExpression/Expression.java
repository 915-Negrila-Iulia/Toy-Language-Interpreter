package app.Model.ToyExpression;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.*;
import app.Model.ToyType.Type;
import app.Model.ToyValue.Value;

public interface Expression {
    /*
        Interface 'Expression'
        Expression objects describe the expressions used in the interpreter;
        returns values

        Abstract methods:
        - eval -> returns the Value of the evaluated Expression
        - typecheck -> returns the Type of Expression and checks for correctness
        - toString -> returns a string representing the expression
        - deepCopy -> returns a copy of the current Expression object
     */

    Value eval(IDictionary<String,Value> table, IHeap<Value> heap) throws Exception;
    Type typecheck(MyDictionary<String,Type> typeEnv) throws Exception;
    String toString();
    Expression deepCopy();
}
