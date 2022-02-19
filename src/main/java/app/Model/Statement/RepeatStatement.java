package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.IStack;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotBoolException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyExpression.NotExpression;
import app.Model.ToyType.BoolType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.BoolValue;
import app.Model.ToyValue.Value;

public class RepeatStatement implements IStatement{

    /*
        RepeatStatement class implements abstract methods of IStatement interface
     */

    private final IStatement stmt;
    private final Expression expr;

    public RepeatStatement(IStatement stmt, Expression expr){
        /*
             Parametrised constructor which creates a RepeatStatement object
            :param expr: condition (Expression type)
            :param stmt: statement to be executed (IStatement type)
         */

        this.stmt = stmt;
        this.expr = expr;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Simulates Repeat Until loop
            If 'expr' is not BoolType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IStack<IStatement> stack = state.getStack();
        IDictionary<String, Value> symTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        Value condition = expr.eval(symTable,heap);
        if(condition.getType().equals(new BoolType())){
            BoolValue bool_value = (BoolValue) condition;

            IStatement new_stmt = new CompoundStatement(stmt,new WhileStatement(new NotExpression(expr),stmt));
            stack.push(new_stmt);
        }
        else
            throw new NotBoolException(expr.toString()+" is not bool type");

        return null;
    };

    @Override
    public String toString(){
        return "repeat{"+stmt.toString()+"}until("+expr.toString()+")";
    }

    @Override
    public IStatement deepCopy(){
        return new WhileStatement(expr.deepCopy(),stmt.deepCopy());
    };

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if type of 'expr' is BoolType
            Checks the correctness of the statement 'stmt' on a deep copy of the given TypeEnvironment
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_expr = expr.typecheck(typeEnv);
        MyDictionary<String,Type> copyEnv = (MyDictionary<String, Type>) typeEnv.deepCopy();
        if(type_expr.equals(new BoolType())){
            stmt.typecheck(copyEnv);
            return typeEnv;
        }
        else
            throw new NotBoolException("repeat stmt: condition must be boolean");
    };
}
