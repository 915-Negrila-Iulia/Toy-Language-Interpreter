package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.ILatchTable;
import app.Model.ADT.IStack;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotIntegerException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

public class AwaitLatch implements IStatement{

    /*
        AwaitLatch class implements abstract methods of IStatement interface
     */

    private final String var;

    public AwaitLatch(String var){
        /*
            Parametrised constructor which creates an AwaitLatch object
            :param var: variable from SymbolTable which is the key for
                        an entry into the LatchTable (String type)
         */

        this.var = var;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Simulates await()
            Retrieves the entry from LatchTable by using 'var' variable
            and checks the value of 'count' in order
            to know whether to release the threads and let them continue running or not
            If the variable is not defined in SymbolTable or is not IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IStack<IStatement> exeStack = state.getStack();
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        ILatchTable<Integer,Integer> latchTable = state.getLatchTable();

        if(symbolTable.isDefined(var)){
            Value val = symbolTable.lookUp(var);
            if(val.getType().equals(new IntType())){
                IntValue val_int = (IntValue) val;
                int foundIndex = val_int.getValue();

                if(latchTable.contains(foundIndex)){
                    if(latchTable.lookUp(foundIndex) != 0){
                        //program must wait for latch to reach 0
                        exeStack.push(this);
                    }
                }
                else
                    throw new RuntimeException(foundIndex+" not an index in LatchTable");
            }
            else
                throw new NotIntegerException(var+" is not int type");
        }
        else
            throw new VariableNotDefinedException(var+" is not defined");

        return null;
    };

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if type of 'var' is IntType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_variable = typeEnv.lookUp(var);
        if(type_variable.equals(new IntType()))
            return typeEnv;
        else
            throw new NotIntegerException(var+" is not int type");
    };

    @Override
    public IStatement deepCopy(){
        return new AwaitLatch(var);
    };

    @Override
    public String toString(){
        return "await("+var+")";
    }
}
