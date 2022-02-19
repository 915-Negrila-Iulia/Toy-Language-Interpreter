package app.Controller;

import app.Model.ADT.*;
import app.Model.ProgramState;
import app.Model.Statement.IStatement;
import app.Model.ToyType.Type;
import app.Model.ToyValue.RefValue;
import app.Model.ToyValue.Value;
import app.Repository.IRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    /*
        Controller object manages the execution of Programs used in the interpreter

        Fields:
        repo -> reference to repository that stores programs (IRepository type)
        executor -> interface which allows us to execute tasks on threads asynchronously (ExecutorService type)
     */

    private final IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo){
        /*
            Parametrised constructor which creates a Controller object
            :param repo: repository which stores the programs to be executed (IRepository type)
         */

        this.repo = repo;
    }

    public IRepository getRepo(){
        return this.repo;
    }

    @Override
    public String toString(){
        return this.repo.getProgramList().get(0).getOriginalProgram().toString();
    }

    List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramsList){
        /*
            Using Java functional style (Java streams) keeps only the programs that are not completed
            (programs that still have statements on their ExecutionStacks) from a given list of programs
            :param inProgramsList: list of programs to be checked if completed (List type)
            :return: list of programs that are not completed (List type)
         */

        return inProgramsList.stream()
                .filter(program -> program.isNotCompleted())
                .collect(Collectors.toList());
    };

    public void oneStepForAllPrograms(List<ProgramState> programsList) throws Exception{
        /*
            Executes one step for each existing program state (namely each thread)
            :param programsList: list of programs to execute (only first statement of each program) (List type)
            :return: -
         */

        /*print the programsList into the log file, before execution*/
        programsList.forEach(program -> {
            try {
                repo.logPrgStateExec(program);
            } catch (Exception e) {
                //System.out.println(e.getMessage());
                throw new RuntimeException(e.getMessage(),e);
            }
        });

        /* run concurrently one step for each of the existing program states;
           prepare list of callables */
        List<Callable<ProgramState>> callList = programsList.stream()
                .map((ProgramState program) -> (Callable<ProgramState>)(()->{return program.oneStep();}))
                .collect(Collectors.toList());

        /* start the execution of the callables */
        /* newProgramsList list contains new created program states (threads) */
        /* invokeAll() - This method executes the list of tasks given and returns
                         the list of Futures which contain the results of all the tasks when completed */
        List<ProgramState> newProgramsList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get(); /* get() - Waits if necessary for the computation to complete,
                                                        and then retrieves its result */
                    } catch (Exception e) {
                        //System.out.println(e.getMessage());
                        throw new RuntimeException(e.getMessage(),e);
                        //return null;
                    }
                })
                .filter(program -> program != null)
                .collect(Collectors.toList());

        /* add the new created threads to the list of existing threads */
        programsList.addAll(newProgramsList);

        /* print the programsList into the logFile, after execution */
        programsList.forEach(program -> {
            try {
                repo.logPrgStateExec(program);
            } catch (Exception e) {
                //System.out.println(e.getMessage());
                throw new RuntimeException(e.getMessage(),e);
            }
        });

        /* save current programs in repo */
        repo.setProgramList(programsList);

    };

    public void allSteps()  throws Exception{
        /*
            Executes all statements of a program
            :return: -
         */

        /* ThreadPool - represents a group of worker threads */
        executor = Executors.newFixedThreadPool(2);

        /* remove completed programs */
        List<ProgramState> programsList = removeCompletedProgram(repo.getProgramList());
        while(programsList.size() > 0){
            /* call garbage collector */
            repo.getProgramList().stream()
                            .forEach(program -> program.getHeap().setContent(safeGarbageCollector(
                                    getReferencedAddresses(program.getSymbolTable().getContent().values(),
                                            program.getHeap().getContent().values()),
                                    program.getHeap().getContent())));
            try {
                oneStepForAllPrograms(programsList);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            /* remove completed programs */
            programsList = removeCompletedProgram(repo.getProgramList());

        }
        executor.shutdownNow();

        /* repo still contains at least one completed program and its List<ProgramState> is not empty;
           note that oneStepForAllPrograms() calls the method setProgramList() of repo in order to change it */
        /* update repo state */
        repo.setProgramList(programsList);

        System.out.println("Program execution done\n\n");
    };

    public void initialize_executor(){
        executor = Executors.newFixedThreadPool(2);
    }

    public void oneStepForAllGUI() throws Exception{
        /*
            Executes one step for each existing program state (namely each thread)
            :return: -
         */

        List<ProgramState> programsList = removeCompletedProgram(repo.getProgramList());
        if(programsList.size() > 0){

            repo.getProgramList().stream()
                    .forEach(program -> program.getHeap().setContent(safeGarbageCollector(
                            getReferencedAddresses(program.getSymbolTable().getContent().values(),
                                    program.getHeap().getContent().values()),
                            program.getHeap().getContent())));

            try {
                oneStepForAllPrograms(programsList);
            }
            catch (Exception e){
                //System.out.println("error");
                throw new Exception(e.getMessage(),e);
            }

        }
        else {
            executor.shutdownNow();
            throw new Exception("Program successfully finished");
        }
    }

    public void typecheck() throws Exception{
        /*
            Creates the TypeEnvironment for the main program
            :return: -
         */
        MyDictionary<String, Type> typeEnv = new MyDictionary<>();

        if(repo.getProgramList().size() != 1){
            throw new Exception("Cannot perform type check");
        }
        else{
            ProgramState current_program = repo.getProgramList().get(0);
            IStack<IStatement> copyStack = current_program.getStack().deepCopy();
            while(!copyStack.isEmpty()){
                IStatement top_statement = copyStack.pop();
                typeEnv = top_statement.typecheck(typeEnv);
            }
        }
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> referencedAddresses, Map<Integer,Value> heap){
        /*
            Simulates a garbage collector
            Removes those addresses which are not referred from the SymbolTable and from other Heap table entries
            by using Java functional style (Java streams)
            :param referencedAddresses: addresses that are used for reference (List type)
            :param heap: content of HeapTable (Map type)
         */

        /* .entrySet() -> returns a set view of all the entries of a hashmap */
        return heap.entrySet().stream()
                .filter(e->referencedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    private List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues){
        /*
            Using Java streams creates a list of addresses used in HeapTable as reference
            :param heapTableValues: values from HeapTable (Collection type)
            :return: list of referenced addresses (List type)
         */

        return heapTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        /*
            Using Java streams creates a list of addresses used in SymbolTable as reference
            :param symTableValues: values from SymbolTable (Collection type)
            :return: list of referenced addresses (List type)
         */

        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getReferencedAddresses(Collection<Value> heapTableValues, Collection<Value> symTableValues){
        /*
            Creates a list containing all referenced addresses
            from both SymbolTable and HeapTable
            :param heapTableValues: values from HeapTable (Collection type)
            :param symTableValues: values from SymbolTable (Collection type)
            :return: list of referenced addresses (List type)
         */

        List<Integer> symTableAddresses = getAddrFromSymTable(symTableValues);
        List<Integer> heapTableAddresses = getAddrFromHeapTable(heapTableValues);
        List<Integer> referencedAddresses = new ArrayList<>();
        referencedAddresses.addAll(symTableAddresses);
        referencedAddresses.addAll(heapTableAddresses);
        return referencedAddresses;
    }
}
