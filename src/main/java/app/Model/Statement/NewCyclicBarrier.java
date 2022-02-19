package app.Model.Statement;

import app.Model.ADT.*;
import app.Model.Exception.NotIntegerException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

import java.util.ArrayList;

public class NewCyclicBarrier implements IStatement{

    /*
        NewCyclicBarrier class implements abstract methods of IStatement interface
     */

    private final String var;
    private final Expression expr;
    private static int nextFree = 0;

    public NewCyclicBarrier(String var, Expression expr){
        /*
            Parametrised constructor which creates a NewCyclicBarrier object
            :param var: variable from SymbolTable which is the key for
                        an entry into the BarrierTable (String type)
            :param expr: expression having the value of the number of threads
                        which have to wait at the CyclicBarrier (Expression type)
         */

        this.var = var;
        this.expr = expr;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Creates a new barrier into the BarrierTable
            In SymbolTable variable 'var' is associated to the entry of the new barrier (nextFree location)
            In BarrierTable at nextFree location a new Pair is added,
            containing the number of threads to wait (value of 'expr') and an empty list
            If the variable is not IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();
        ICyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier = state.getCyclicBarrier();

        Value val = expr.eval(symbolTable,heap);
        if (val.getType().equals(new IntType())) {
            synchronized (cyclicBarrier) {
                IntValue val_int = (IntValue) val;
                Integer number = val_int.getValue();
                ArrayList<Integer> emptyArray = new ArrayList<>();
                cyclicBarrier.add(nextFree,new Pair(number,emptyArray));

                if (symbolTable.isDefined(var)) {
                    symbolTable.update(var, new IntValue(nextFree));
                } else {
                    symbolTable.add(var, new IntValue(nextFree));
                }
                nextFree++;
            }
        }
        else{
            throw new NotIntegerException(var+" is not int type");
        }

        return null;
    };

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if types of 'var' and 'expr' are IntType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_variable = typeEnv.lookUp(var);
        Type type_expression = expr.typecheck(typeEnv);
        if(type_variable.equals(new IntType()))
            if(type_expression.equals(new IntType()))
                return typeEnv;
            else
                throw new NotIntegerException(expr.toString()+" is not type int");
        else
            throw new NotIntegerException(var+ " is not type int");

    };

    @Override
    public IStatement deepCopy(){
        return new NewCyclicBarrier(var,expr.deepCopy());
    };

    @Override
    public String toString(){
        return "newBarrier("+var+","+expr.toString()+")";
    }
}

