package app.Model.Statement;

import app.Model.ADT.*;
import app.Model.ProgramState;
import app.Model.ToyType.Type;
import app.Model.ToyValue.StringValue;
import app.Model.ToyValue.Value;

import java.io.BufferedReader;
import java.util.ArrayList;

public class ForkStatement implements IStatement{

    /*
        ForkStatement class implements abstract methods of IStatement interface
     */

    private final IStatement statement;

    public ForkStatement(IStatement stmt){
        /*
            Parametrised constructor which creates a ForkStatement object
            :param stmt: new program executed by a thread (IStatement type)
         */

        this.statement = stmt;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Creates a new ProgramState which runs in parallel with the main ProgramState
            and the other ProgramStates currently running (concurrent programming)
            HeapTable, FileTable and OutputTable are shared by all ProgramStates
            Each ProgramState has its own new instance of ExecutionStack and its own deep copy of the SymbolTable;
            these two are not shared with the parent thread
            :param state: state of the program (ProgramState type)
            :return: new ProgramState
         */

        IStack<IStatement> stack = new MyStack<>();
        IDictionary<String, Value> symTable = state.getSymbolTable().deepCopy();
        IList<Value> output = state.getOutputList();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Value> heap = state.getHeap();
        ICyclicBarrier<Integer,Pair<Integer, ArrayList<Integer>>> cyclicBarrier = state.getCyclicBarrier();
        ILatchTable<Integer,Integer> latchTable = state.getLatchTable();
        ILockTable<Integer,Integer> lockTable = state.getLockTable();
        ISemaphore<Integer,Pair<Integer, ArrayList<Integer>>> semaphoreTable = state.getSemaphoreTable();

        ProgramState newState = new ProgramState(stack,symTable,output,fileTable,heap,cyclicBarrier,latchTable,lockTable,semaphoreTable, statement);
        return newState;
    }

    @Override
    public IStatement deepCopy(){
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString(){
        return "fork("+ statement.toString()+")";
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks the correctness of the statement on a deep copy of the given TypeEnvironment
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        MyDictionary<String,Type> copyEnv = (MyDictionary<String, Type>) typeEnv.deepCopy();
        statement.typecheck(copyEnv);
        return typeEnv;
    }


}
