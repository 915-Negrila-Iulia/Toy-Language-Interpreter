package app.Model;

import app.Model.ADT.*;
import app.Model.Exception.StackIsEmptyException;
import app.Model.Statement.CompoundStatement;
import app.Model.Statement.IStatement;
import app.Model.ToyValue.StringValue;
import app.Model.ToyValue.Value;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;

public class ProgramState {

    /*
        ProgramState objects describe the programs that will be interpreted and run
     */

    private final IStack<IStatement> executionStack;
    private final IDictionary<String, Value> symbolTable;
    private final IList<Value> output;
    private final IDictionary<StringValue, BufferedReader> fileTable;
    private final IHeap<Value> heap;
    private final ICyclicBarrier<Integer,Pair<Integer, ArrayList<Integer>>> cyclicBarrier;
    private final ILatchTable<Integer,Integer> latchTable;
    private final ILockTable<Integer,Integer> lockTable;
    private final ISemaphore<Integer, Pair<Integer,ArrayList<Integer>>> semaphoreTable;
    private final IStatement originalProgram;

    private final int id;
    public static int newId = 0;

    public ProgramState(IStack<IStatement> stack, IDictionary<String, Value> table,
                 IList<Value> list, IDictionary<StringValue, BufferedReader> fileTable,
                        IHeap<Value> heap, ICyclicBarrier<Integer,Pair<Integer, ArrayList<Integer>>> cyclicBarrier,
                        ILatchTable<Integer,Integer> latchTable, ILockTable<Integer,Integer> lockTable,
                        ISemaphore<Integer, Pair<Integer,ArrayList<Integer>>> semaphoreTable,IStatement program) {
        /*
            Parametrised constructor which creates an ProgramState object
            :param stack: ExecutionStack of the program, to store statements (IStack type)
            :param table: SymbolTable of the program, to associate variable names with Values (IDictionary type)
            :param list: OutputTable of the program, to print results (IList type)
            :param fileTable: FileTable of the program, to associate file names with BufferReaders objects (IDictionary type)
            :param heap: HeapTable of the program, to store values at free addresses (IHeap type)
            :param cyclicBarrier: CyclicBarrierTable of the program, to synchronize programs (threads) (ICyclicBarrier type)
            :param latchTable: LatchTable of the program, to synchronize programs (threads) (ILatchTable type)
            :param lockTable: LockTable of the program, to synchronize programs (threads) (ILockTable type)
            :param program: original program (IStatement type)
         */

        this.id = getNewProgramStateID();
        this.executionStack = stack;
        this.symbolTable = table;
        this.output = list;
        this.fileTable = fileTable;
        this.heap = heap;
        this.cyclicBarrier = cyclicBarrier;
        this.latchTable = latchTable;
        this.lockTable = lockTable;
        this.semaphoreTable = semaphoreTable;

        this.originalProgram = program.deepCopy();
        stack.push(program);
    }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public int getID(){return this.id;}

    public synchronized int getNewProgramStateID(){
        /*
            Computes the unique id of the ProgramState
            :return: id of program
         */

        newId+=1;
        return newId;
    }

    public IStack<IStatement> getStack(){return this.executionStack;}

    public IDictionary<String,Value> getSymbolTable(){return this.symbolTable;}

    public IList<Value> getOutputList(){return this.output;}

    public IDictionary<StringValue, BufferedReader> getFileTable(){return this.fileTable;}

    public IHeap<Value> getHeap(){return this.heap;}

    public ICyclicBarrier<Integer,Pair<Integer, ArrayList<Integer>>> getCyclicBarrier(){return this.cyclicBarrier;}

    public ILatchTable<Integer,Integer> getLatchTable(){return this.latchTable;}

    public ILockTable<Integer,Integer> getLockTable(){return this.lockTable;}

    public ISemaphore<Integer, Pair<Integer,ArrayList<Integer>>> getSemaphoreTable(){return this.semaphoreTable;};

    public Boolean isNotCompleted(){
        /*
            Checks if Program is finished or not
        */

        if(this.executionStack.isEmpty())
            return false;
        else
            return true;
    }

    public ProgramState oneStep() throws Exception{
        /*
            Executes one step for the current program state
            Executes the statement from the top of the ExecutionStack of the ProgramState
            :return: updated ProgramState
         */

        if(this.executionStack.isEmpty())
            throw new StackIsEmptyException("program state stack is empty");

        IStatement current_stmt = this.executionStack.pop();
        return current_stmt.execute(this);
    }



    public String inorderTraversal(IStatement statement){
        /*
            Computes the string version of a statement by performing an inorderTraversal
            :param statement: statement to be traversed inorder (IStatement type)
            :return: statement as string
         */

        if(statement instanceof CompoundStatement){

            CompoundStatement compoundStatement = (CompoundStatement) statement;
            IStatement left = compoundStatement.getFirst();
            IStatement right = compoundStatement.getSecond();
            String statementString="";

            if(left instanceof CompoundStatement){
                statementString += inorderTraversal(left);

            }else{
                statementString += left.toString()+"\n";
            }

            if(right instanceof CompoundStatement){
                statementString += inorderTraversal(right);

            }else{
                statementString += right.toString()+"\n";
            }

            return statementString;
        }else {
            return statement.toString()+"\n";
        }
    }

    public String stackToString(){
        /*
            :return: ExecutionStack as string
         */

        String finalString = "ExeStack\n";
        String stackString = "";
        Iterator<IStatement> iterator = this.executionStack.iterator();
        while(iterator.hasNext()){
            IStatement statement = iterator.next();
            stackString = inorderTraversal(statement) + stackString;
        }
        finalString +=stackString;
        return finalString;
    }

    public String symTableToString(){
        /*
            :return: SymbolTable as string
         */

        String symTableString = "SymTable\n";
        IDictionary<String, Value> symTable = this.symbolTable;
        for(String key: symTable.keySet()){
            symTableString += key;
            symTableString += " --> ";
            try {
                symTableString += symTable.lookUp(key).toString();
            }catch (Exception ignored){

            }
            symTableString += "\n";
        }
        return symTableString;
    }

    public String outputToString(){
        /*
            :return: OutputTable as string
         */

        String outputString = "Out\n";
        IList<Value> outputList = this.output;
        for(int i=0; i<outputList.size(); i++){
            outputString += outputList.get(i).toString();
            outputString += "\n";
        }
        return outputString;
    }

    public String fileTableToString(){
        /*
            :return: FileTable as string
         */

        String tableString = "FileTable\n";
        IDictionary<StringValue, BufferedReader> table = this.fileTable;
        for(StringValue key: table.keySet()){
            tableString += key.toString();
            tableString += "\n";
        }
        return tableString;
    }

    public String heapToString(){
        /*
            :return: HeapTable as string
         */

        String heapString = "Heap\n";
        IHeap<Value> heap = this.heap;
        for(Integer key: heap.getKeys()){
            heapString += key.toString();
            heapString += " --> ";
            try {
                heapString += heap.lookUp(key).toString();
            }catch (Exception ignored){

            }
            heapString += "\n";
        }
        return heapString;
    }

    public String cyclicBarrierToString(){
        /*
            :return: CyclicBarrierTable as string
         */

        String barrierString = "CyclicBarrierTable\n";
        ICyclicBarrier<Integer,Pair<Integer, ArrayList<Integer>>> cyclicBarrier = this.cyclicBarrier;
        for(Integer key: cyclicBarrier.keySet()){
            barrierString += key.toString();
            barrierString += " --> ";
            try {
                barrierString += cyclicBarrier.lookUp(key).toString();
            }catch (Exception ignored){

            }
            barrierString += "\n";
        }
        return barrierString;
    }

    public String latchTableToString(){
        /*
            :return: LatchTable as string
         */

        String latchString = "LatchTable\n";
        ILatchTable<Integer,Integer> latchTable = this.latchTable;
        for(Integer key: latchTable.keys()){
            latchString += key.toString();
            latchString += " --> ";
            try {
                latchString += latchTable.lookUp(key).toString();
            }catch (Exception ignored){

            }
            latchString += "\n";
        }
        return latchString;
    }

    public String lockTableToString(){
        /*
            :return: LockTable as string
         */

        String lockTableString = "LockTable\n";
        ILockTable<Integer, Integer> lockTable = this.lockTable;
        for(Integer key: lockTable.keySet()){
            lockTableString += key.toString();
            lockTableString += " --> ";
            try {
                lockTableString += lockTable.getValue(key).toString();
            }catch (Exception ignored){

            }
            lockTableString += "\n";
        }
        return lockTableString;
    }

    public String semaphoreToString(){
        /*
            :return: SemaphoreTable as string
         */

        String semaphoreString = "SemaphoreTable\n";
        ISemaphore<Integer, Pair<Integer,ArrayList<Integer>>> semaphoreTable = this.semaphoreTable;
        for(Integer key: semaphoreTable.keys()){
            semaphoreString += key.toString();
            semaphoreString += " --> ";
            try {
                semaphoreString += semaphoreTable.lookUp(key).toString();
            }catch (Exception ignored){

            }
            semaphoreString += "\n";
        }
        return semaphoreString;
    }


    public String toString(){
        /*
            :return: ProgramState as string
         */

        String program="";
        program += "id: "+ this.id + "\n";
        program += stackToString();
        program += symTableToString();
        program += outputToString();
        program += fileTableToString();
        program += heapToString();
        program += cyclicBarrierToString();
        program += latchTableToString();
        program += lockTableToString();
        program += semaphoreToString();
        program +="--------------------\n";

        return program;
    }
}
