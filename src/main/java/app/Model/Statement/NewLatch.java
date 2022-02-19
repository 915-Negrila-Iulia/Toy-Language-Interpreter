package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.ILatchTable;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotIntegerException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

public class NewLatch implements IStatement{

    /*
        NewLatch class implements abstract methods of IStatement interface
     */

    private final String var;
    private final Expression expr;
    private static int nextFree = 0;

    public NewLatch(String var, Expression expr){
        /*
            Parametrised constructor which creates a NewLatch object
            :param var: variable from SymbolTable which is the key for
                        an entry into the LatchTable (String type)
            :param expr: expression having the value of 'count';
                         threads are waiting until 'count' reaches zero (Expression type)
         */

        this.var = var;
        this.expr = expr;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Creates a new entry into the LatchTable
            In SymbolTable variable 'var' is associated to the entry of the new latch (nextFree location)
            In LatchTable at nextFree location is added the initial value of 'count'
            If the variable is not defined or is not IntType a custom Expression is thrown
            If 'expr' does not have IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();
        ILatchTable<Integer,Integer> latchTable = state.getLatchTable();

        Value val = expr.eval(symbolTable,heap);
        if (val.getType().equals(new IntType())){
            IntValue val_int = (IntValue) val;
            int num1 = val_int.getValue();

            synchronized (latchTable) {
                latchTable.add(nextFree,num1);

                if(symbolTable.isDefined(var)){
                    if(symbolTable.lookUp(var).getType().equals(new IntType())){
                        symbolTable.update(var,new IntValue(nextFree));
                    }
                    else
                        throw new NotIntegerException(var+" is not int type");
                }
                else
                    throw new VariableNotDefinedException(var+" is not defined");

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
        return new NewLatch(var,expr.deepCopy());
    };

    @Override
    public String toString(){
        return "NewLatch("+var+","+expr.toString()+")";
    }
}

