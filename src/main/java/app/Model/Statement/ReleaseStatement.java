package app.Model.Statement;

import app.Model.ADT.*;
import app.Model.Exception.NotIntegerException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

import java.util.ArrayList;

public class ReleaseStatement implements IStatement{

    /*
        ReleaseStatement class implements abstract methods of IStatement interface
     */

    private final String var;

    public ReleaseStatement(String var){
        /*
            Parametrised constructor which creates a ReleaseStatement object
            :param var: variable from SymbolTable which is the key for
                        an entry into the SemaphoreTable (String type)
         */

        this.var = var;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Simulates release()
            Retrieves the entry from SemaphoreTable by using 'var' variable
            and checks if the thread has a permit; if so, then the current program (thread) releases the permit
            If the variable is not defined in SymbolTable or is not IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IStack<IStatement> exeStack = state.getStack();
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();
        ISemaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable = state.getSemaphoreTable();

        if(symbolTable.isDefined(var)) {
            Value val = symbolTable.lookUp(var);

            if(val.getType().equals(new IntType())) {
                IntValue val_int = (IntValue) val;
                int foundIndex = val_int.getValue();

                synchronized (semaphoreTable) {
                    if (semaphoreTable.contains(foundIndex)) {
                        Pair<Integer, ArrayList<Integer>> pair = semaphoreTable.lookUp(foundIndex);
                        Integer N1 = pair.getFirst();
                        ArrayList<Integer> List1 = pair.getSecond();
                        int programID = state.getID();

                        if (List1.contains(programID)) {
                            List1.removeIf(elem -> elem.equals(programID));
                        }
                    } else
                        throw new RuntimeException(foundIndex + " is not an index in SemaphoreTable");

                }
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
        return new ReleaseStatement(var);
    };

    @Override
    public String toString(){
        return "release("+var+")";
    }
}
