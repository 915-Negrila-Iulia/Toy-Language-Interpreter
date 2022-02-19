package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.IStack;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotBoolException;
import app.Model.Exception.TypesDontMatchException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.BoolType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.Value;

public class ConditionalAssignment implements IStatement{

    /*
        ConditionalAssignment class implements abstract methods of IStatement interface
     */

    private final String id;
    private final Expression expr1;
    private final Expression expr2;
    private final Expression expr3;

    public ConditionalAssignment(String id, Expression e1, Expression e2, Expression e3){
        /*
            Parametrised constructor which creates a ConditionalAssignment object
            :param id: name of a variable (String type)
            :param e1: condition (Expression type)
            :param e2: value associated to variable 'id' if condition is true (Expression type)
            :param e3: value associated to variable 'id' if condition is false (Expression type)
         */

        this.id = id;
        this.expr1 = e1;
        this.expr2 = e2;
        this.expr3 = e3;
    }

    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Assigns a value to a variable depending on a given condition
            Creates a new instance of AssignmentStatement and pushes it on the ExecutionStack
            If the condition ('expr1') is not BoolType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IStack<IStatement> stack = state.getStack();
        IDictionary<String, Value> table = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        Value condition = expr1.eval(table,heap);
        if(condition.getType().equals(new BoolType())){

            IStatement new_stmt = new IfStatement(expr1,
                    new AssignmentStatement(id,expr2), new AssignmentStatement(id,expr3));
            stack.push(new_stmt);

        }else{
            throw new NotBoolException(expr1.toString()+" is not bool type");
        }

        return null;

    };

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if types of 'id', 'expr2' and 'expr3' are the same
            Also checks if 'expr1' has type BoolType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_condition = expr1.typecheck(typeEnv);
        Type type_variable = typeEnv.lookUp(id);
        Type type_expression2 = expr2.typecheck(typeEnv);
        Type type_expression3 = expr3.typecheck(typeEnv);
        if(type_condition.equals(new BoolType())){
            if(type_variable.equals(type_expression2)){
                if(type_variable.equals(type_expression3)){
                    return typeEnv;
                }
                else
                    throw new TypesDontMatchException(id+" and "+expr3.toString()+" have different types ");
            }
            else
                throw new TypesDontMatchException(id+" and "+expr2.toString()+" have different types ");
        }
        else
            throw new NotBoolException(expr1.toString()+" must be boolean");
    };

    @Override
    public IStatement deepCopy(){
        return new ConditionalAssignment(id,expr1.deepCopy(),expr2.deepCopy(),expr3.deepCopy());
    };

    @Override
    public String toString(){
        return id+"=("+expr1.toString()+")?"+expr2.toString()+":"+expr3.toString();
    }
}