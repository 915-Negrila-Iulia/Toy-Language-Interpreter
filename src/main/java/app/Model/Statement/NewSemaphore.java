package app.Model.Statement;

import app.Model.ADT.*;
import app.Model.Exception.NotIntegerException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

import java.util.ArrayList;

public class NewSemaphore implements IStatement {

    /*
        NewSemaphore class implements abstract methods of IStatement interface
     */

    private final String var;
    private final Expression expr;
    private static int nextFree = 0;

    public NewSemaphore(String var, Expression expr){
        /*
            Parametrised constructor which creates a NewSemaphore object
            :param var: variable from SymbolTable which is the key for
                        an entry into the SemaphoreTable (String type)
            :param expr: expression having the value of 'permits';
                         only 'permits' number of threads can enter a critical section at a time (Expression type)
         */

        this.var = var;
        this.expr = expr;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Creates a new entry into the SemaphoreTable
            In SymbolTable variable 'var' is associated to the entry of the new semaphore (nextFree location)
            In SemaphoreTable at nextFree location a new Pair is added,
            containing the number of 'permits' (value of 'expr') and an empty list
            If the variable is not defined or is not IntType a custom Expression is thrown
            If 'expr' does not have IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();
        ISemaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable = state.getSemaphoreTable();

        Value val = expr.eval(symbolTable,heap);
        if (val.getType().equals(new IntType())) {
            IntValue val_int = (IntValue) val;
            int number1 = val_int.getValue();

            synchronized (semaphoreTable) {
                ArrayList<Integer> emptyArray = new ArrayList<>();
                semaphoreTable.add(nextFree, new Pair(number1,emptyArray));

                if (symbolTable.isDefined(var)) {
                    if (symbolTable.lookUp(var).getType().equals(new IntType())) {
                        symbolTable.update(var, new IntValue(nextFree));
                    } else
                        throw new NotIntegerException(var + " is not int type");
                } else
                    throw new VariableNotDefinedException(var + " is not defined");

                nextFree++;
            }
        }
        else
            throw new NotIntegerException(expr.toString()+" is not integer");

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
            if(type_expression.equals(type_expression))
                return typeEnv;
            else
                throw new NotIntegerException(expr.toString()+" is not int type");
        else
            throw new NotIntegerException(var+" is not int type");
    };

    @Override
    public IStatement deepCopy(){
        return new NewSemaphore(var,expr.deepCopy());
    };

    @Override
    public String toString(){
        return "createSemaphore("+var+","+expr.toString()+")";
    }
}
