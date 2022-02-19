package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.IStack;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotBoolException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.BoolType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.BoolValue;
import app.Model.ToyValue.Value;

public class IfStatement implements IStatement{

    /*
        IfStatement class implements abstract methods of IStatement interface
     */

    private final Expression conditionExpr;
    private final IStatement ifThenStatement;
    private final IStatement elseStatement;

    public IfStatement(Expression expr, IStatement ifTrue, IStatement ifFalse){
        /*
             Parametrised constructor which creates a IfStatement object
            :param expr: condition (Expression type)
            :param ifTrue: statement to execute if condition is true (IStatement type)
            :param ifFalse: statement to execute if condition is false (IStatement type)
         */

        this.conditionExpr = expr;
        this.ifThenStatement = ifTrue;
        this.elseStatement = ifFalse;
    }

    @Override
    public IStatement deepCopy(){
        return new IfStatement(conditionExpr.deepCopy(),ifThenStatement.deepCopy(),elseStatement.deepCopy());
    }

    @Override
    public String toString(){
        return "If "+conditionExpr.toString()+" Then "+ifThenStatement.toString()+" Else "+elseStatement.toString();
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Simulates If statement
            Chooses one statement to be pushed on ExecutionStack from two given statements,
            depending on a given condition expression that is evaluated
            If condition expression is not BoolType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IStack<IStatement> stack = state.getStack();
        IDictionary<String,Value> table = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        try {
            Value condition = conditionExpr.eval(table,heap);
            if(condition.getType().equals(new BoolType())){

                BoolValue conditionBool = (BoolValue) condition;

                if(conditionBool.getValue()==true){
                    stack.push(ifThenStatement);
                }
                else{
                    stack.push(elseStatement);
                }

            }else{
                throw new NotBoolException("condition is not bool type");
            }
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if condition expression is BoolType
            Checks the correctness of the two statements on a deep copy of the given TypeEnvironment
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_condition = conditionExpr.typecheck(typeEnv);
        MyDictionary<String, Type> copyEnv1 = (MyDictionary<String, Type>) typeEnv.deepCopy();
        MyDictionary<String, Type> copyEnv2 = (MyDictionary<String, Type>) typeEnv.deepCopy();
        if(type_condition.equals(new BoolType())){
            ifThenStatement.typecheck(copyEnv1);
            elseStatement.typecheck(copyEnv2);
            return typeEnv;
        }
        else
            throw new NotBoolException("condition of IfStatement must be boolean");
    };

}
