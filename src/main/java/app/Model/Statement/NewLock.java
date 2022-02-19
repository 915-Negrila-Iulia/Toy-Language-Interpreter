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

public class NewLock implements IStatement {

    /*
        NewLock class implements abstract methods of IStatement interface
     */

    private final String var;
    private static int nextFree = 0;

    public NewLock(String var){
        /*
            Parametrised constructor which creates a NewLock object
            :param var: variable from SymbolTable which is the key for
                        an entry into the LockTable (String type)
         */

        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception{
        /*
            Creates a new entry into the LockTable
            In SymbolTable variable 'var' is associated to the entry of the new lock (nextFree location)
            In LockTable at nextFree location is added an initial value equal to -1;
            this means that no thread has the lock at the moment
            If the variable is not defined or is not IntType a custom Expression is thrown
            If 'expr' does not have IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        ILockTable<Integer, Integer> lockTable = state.getLockTable();
        IDictionary<String, Value> symTable = state.getSymbolTable();

        synchronized (lockTable) {
            lockTable.add(nextFree, -1);


            if (symTable.isDefined(var)) {
                Value var_value = symTable.lookUp(var);

                if (var_value.getType().equals(new IntType()))
                    symTable.update(var, new IntValue(nextFree));
                else
                    throw new NotIntegerException(var + " is not int type");
            } else {
                throw new VariableNotDefinedException(var + " is not defined");
            }
            nextFree++;

        }

        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if types of 'var' is IntType
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
    public IStatement deepCopy(){ return new NewLock(var);};

    @Override
    public String toString(){
        return "newLock("+ var +")";
    }

}

