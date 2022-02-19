package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.ILockTable;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotIntegerException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

public class UnlockStatement implements IStatement{

    /*
        UnlockStatement class implements abstract methods of IStatement interface
     */

    private final String var;

    public UnlockStatement(String var){
        /*
            Parametrised constructor which creates a UnlockStatement object
            :param var: variable from SymbolTable which is the key for
                        an entry into the LockTable (String type)
         */

        this.var = var;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Simulates unlock()
            Retrieves the entry from LockTable by using 'var' variable
            and checks if the current thread has the lock; if so, then current program (thread) releases the lock
            If the variable is not defined in SymbolTable or is not IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        ILockTable<Integer, Integer> lockTable = state.getLockTable();
        IDictionary<String, Value> symTable = state.getSymbolTable();

        if(symTable.isDefined(var)) {
            Value var_value = symTable.lookUp(var);

            if(var_value.getType().equals(new IntType())){
                IntValue var_int = (IntValue) var_value;
                int foundIndex = var_int.getValue();

                synchronized (lockTable) {
                    if (lockTable.contains(foundIndex)) {
                        if (lockTable.getValue(foundIndex) == state.getID()) {
                            lockTable.update(foundIndex, -1);
                        }
                    }
                    else
                        throw new RuntimeException(foundIndex + " is not an index in LockTable");
                }

            }
            else
                throw new NotIntegerException(var+" is not int type");
        }
        else {
            throw new VariableNotDefinedException(var+" is not defined");
        }

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
            throw new NotIntegerException(var+ " is not type int");
    };

    @Override
    public IStatement deepCopy(){
        return new UnlockStatement(var);
    };

    @Override
    public String toString(){
        return "unlock("+ var +")";
    }
}

