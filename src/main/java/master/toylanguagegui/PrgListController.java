package master.toylanguagegui;

import app.Controller.Controller;
import app.Model.ADT.*;
import app.Model.ProgramState;
import app.Model.Statement.*;
import app.Model.ToyExpression.*;
import app.Model.ToyType.BoolType;
import app.Model.ToyType.IntType;
import app.Model.ToyType.RefType;
import app.Model.ToyType.StringType;
import app.Model.ToyValue.BoolValue;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.StringValue;
import app.Model.ToyValue.Value;
import app.Repository.IRepository;
import app.Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrgListController implements Initializable{

    /*
        Creates hardcoded programs from which the user can choose which one to execute
        Creates an observable list from which the user can select the command that is going to
        be executed by the controller
     */

    static Repository repository1,repository2,repository3,repository4,repository5,repository6,repository7,repository8,
            repository9,repository10,repository11,repository12,repository13,repository14,repository15,repository16,
            repository17,repository18, repository19,repository20,repository21,repository22;
    static Controller controller1,controller2,controller3,controller4,controller5,controller6,controller7,controller8,
            controller9,controller10,controller11,controller12,controller13,controller14,controller15,controller16,
            controller17,controller18, controller19,controller20,controller21,controller22;
    static IStatement example1,example2,example3,example4,example5,example6,example7,example8,
            example9,example10,example11,example12,example13,example14,example15,example16,
            example17,example18, example19,example20,example21,example22;

    @FXML
    TextField textPrograms;
    @FXML
    ListView<Controller> ProgramsListView;
    @FXML
    Button runButton;

    public void setUp(){

        String path = "D:\\An2 Sem1\\MAP\\ToyLanguageInterpreter_FinalVersion\\src\\main\\java\\app\\LogFiles\\";

        MyStack<IStatement> exeStack1 = new MyStack<>();
        MyDictionary<String, Value> symTable1 = new MyDictionary<>();
        MyList<Value> output1 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>();
        MyHeap<Value> heap1 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier1 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable1 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable1 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable1 = new Semaphore<>();

        example1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        ProgramState program1 = new ProgramState(exeStack1, symTable1, output1, fileTable1, heap1, cyclicBarrier1, latchTable1, lockTable1, semaphoreTable1, example1);
        repository1 = new Repository(program1, path + "log1.txt");
        controller1 = new Controller(repository1);

        MyStack<IStatement> exeStack2 = new MyStack<>();
        MyDictionary<String, Value> symTable2 = new MyDictionary<>();
        MyList<Value> output2 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        MyHeap<Value> heap2 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier2 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable2 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable2 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable2 = new Semaphore<>();

        example2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a",
                                new ArithmeticExpression(1, new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(3, new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticExpression(1, new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        ProgramState program2 = new ProgramState(exeStack2, symTable2, output2, fileTable2, heap2, cyclicBarrier2, latchTable2, lockTable2, semaphoreTable2, example2);
        repository2 = new Repository(program2, path + "log2.txt");
        controller2 = new Controller(repository2);

        MyStack<IStatement> exeStack3 = new MyStack<>();
        MyDictionary<String, Value> symTable3 = new MyDictionary<>();
        MyList<Value> output3 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>();
        MyHeap<Value> heap3 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier3 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable3 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable3 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable3 = new Semaphore<>();

        example3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        ProgramState program3 = new ProgramState(exeStack3, symTable3, output3, fileTable3, heap3, cyclicBarrier3, latchTable3, lockTable3, semaphoreTable3, example3);
        repository3 = new Repository(program3, path + "log3.txt");
        controller3 = new Controller(repository3);

        MyStack<IStatement> exeStack4 = new MyStack<>();
        MyDictionary<String, Value> symTable4 = new MyDictionary<>();
        MyList<Value> output4 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>();
        MyHeap<Value> heap4 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier4 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable4 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable4 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable4 = new Semaphore<>();

        example4 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new BoolType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new BoolValue(false))),
                                        new PrintStatement(new LogicExpression(2, new VariableExpression("a"),
                                                new VariableExpression("b")))))));

        ProgramState program4 = new ProgramState(exeStack4, symTable4, output4, fileTable4, heap4, cyclicBarrier4, latchTable4, lockTable4, semaphoreTable4, example4);
        repository4 = new Repository(program4, path + "log4.txt");
        controller4 = new Controller(repository4);

        MyStack<IStatement> exeStack5 = new MyStack<>();
        MyDictionary<String, Value> symTable5 = new MyDictionary<>();
        MyList<Value> output5 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable5 = new MyDictionary<>();
        MyHeap<Value> heap5 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier5 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable5 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable5 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable5 = new Semaphore<>();

        example5 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(
                        new StringValue("D:\\JAVAProjects\\InterpretorToyLanguage\\src\\test.in"))),
                        new CompoundStatement(new OpenRFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));

        ProgramState program5 = new ProgramState(exeStack5, symTable5, output5, fileTable5, heap5, cyclicBarrier5, latchTable5, lockTable5, semaphoreTable5, example5);
        repository5 = new Repository(program5, path + "log5.txt");
        controller5 = new Controller(repository5);

        MyStack<IStatement> exeStack6 = new MyStack<>();
        MyDictionary<String, Value> symTable6 = new MyDictionary<>();
        MyList<Value> output6 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable6 = new MyDictionary<>();
        MyHeap<Value> heap6 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier6 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable6 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable6 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable6 = new Semaphore<>();

        example6 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));

        ProgramState program6 = new ProgramState(exeStack6, symTable6, output6, fileTable6, heap6, cyclicBarrier6, latchTable6, lockTable6, semaphoreTable6, example6);
        repository6 = new Repository(program6, path + "log6.txt");
        controller6 = new Controller(repository6);

        MyStack<IStatement> exeStack7 = new MyStack<>();
        MyDictionary<String, Value> symTable7 = new MyDictionary<>();
        MyList<Value> output7 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable7 = new MyDictionary<>();
        MyHeap<Value> heap7 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier7 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable7 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable7 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable7 = new Semaphore<>();

        example7 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(1, new HeapReading(new HeapReading(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5)))))))));

        ProgramState program7 = new ProgramState(exeStack7, symTable7, output7, fileTable7, heap7, cyclicBarrier7, latchTable7, lockTable7, semaphoreTable7, example7);
        repository7 = new Repository(program7, path + "log7.txt");
        controller7 = new Controller(repository7);

        MyStack<IStatement> exeStack8 = new MyStack<>();
        MyDictionary<String, Value> symTable8 = new MyDictionary<>();
        MyList<Value> output8 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable8 = new MyDictionary<>();
        MyHeap<Value> heap8 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier8 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable8 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable8 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable8 = new Semaphore<>();

        example8 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                                new CompoundStatement(new HeapWriting("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(1, new HeapReading(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5))))))));
        ProgramState program8 = new ProgramState(exeStack8, symTable8, output8, fileTable8, heap8, cyclicBarrier8, latchTable8, lockTable8, semaphoreTable8, example8);
        repository8 = new Repository(program8, path + "log8.txt");
        controller8 = new Controller(repository8);

        MyStack<IStatement> exeStack9 = new MyStack<>();
        MyDictionary<String, Value> symTable9 = new MyDictionary<>();
        MyList<Value> output9 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable9 = new MyDictionary<>();
        MyHeap<Value> heap9 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier9 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable9 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable9 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable9 = new Semaphore<>();

        example9 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReading(new HeapReading(new VariableExpression("a")))))))));
        ProgramState program9 = new ProgramState(exeStack9, symTable9, output9, fileTable9, heap9, cyclicBarrier9, latchTable9, lockTable9, semaphoreTable9, example9);
        repository9 = new Repository(program9, path + "log9.txt");
        controller9 = new Controller(repository9);

        MyStack<IStatement> exeStack10 = new MyStack<>();
        MyDictionary<String, Value> symTable10 = new MyDictionary<>();
        MyList<Value> output10 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable10 = new MyDictionary<>();
        MyHeap<Value> heap10 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier10 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable10 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable10 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable10 = new Semaphore<>();

        example10 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"),
                                new ValueExpression(new IntValue(0)), ">"), new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new AssignmentStatement("v", new ArithmeticExpression(2, new VariableExpression("v"),
                                        new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v")))));

        ProgramState program10 = new ProgramState(exeStack10, symTable10, output10, fileTable10, heap10, cyclicBarrier10, latchTable10, lockTable10, semaphoreTable10, example10);
        repository10 = new Repository(program10, path + "log10.txt");
        controller10 = new Controller(repository10);

        MyStack<IStatement> exeStack11 = new MyStack<>();
        MyDictionary<String, Value> symTable11 = new MyDictionary<>();
        MyList<Value> output11 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable11 = new MyDictionary<>();
        MyHeap<Value> heap11 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier11 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable11 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable11 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable11 = new Semaphore<>();

        example11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocation("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWriting("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new HeapReading(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReading(new VariableExpression("a")))))))));

        ProgramState program11 = new ProgramState(exeStack11, symTable11, output11, fileTable11, heap11, cyclicBarrier11, latchTable11, lockTable11, semaphoreTable11, example11);
        repository11 = new Repository(program11, path + "log11.txt");
        controller11 = new Controller(repository11);

        MyStack<IStatement> exeStack12 = new MyStack<>();
        MyDictionary<String, Value> symTable12 = new MyDictionary<>();
        MyList<Value> output12 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable12 = new MyDictionary<>();
        MyHeap<Value> heap12 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier12 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable12 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable12 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable12 = new Semaphore<>();

        example12 = new CompoundStatement(new VariableDeclarationStatement("v", new BoolType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(40))),
                        new PrintStatement(new VariableExpression("v"))));

        ProgramState program12 = new ProgramState(exeStack12, symTable12, output12, fileTable12, heap12, cyclicBarrier12, latchTable12, lockTable12, semaphoreTable12, example12);
        repository12 = new Repository(program12, path + "log12.txt");
        controller12 = new Controller(repository12);

        MyStack<IStatement> exeStack13 = new MyStack<>();
        MyDictionary<String, Value> symTable13 = new MyDictionary<>();
        MyList<Value> output13 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable13 = new MyDictionary<>();
        MyHeap<Value> heap13 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier13 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable13 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable13 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable13 = new Semaphore<>();

        example13 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new ForStatement("v", new ValueExpression(new IntValue(0)),
                                new ValueExpression(new IntValue(3)), new ArithmeticExpression(1,
                                new VariableExpression("v"), new ValueExpression(new IntValue(1))),
                                new ForkStatement(new CompoundStatement(new PrintStatement(
                                        new VariableExpression("v")), new AssignmentStatement(
                                        "v", new ArithmeticExpression(1, new VariableExpression("v"),
                                        new ValueExpression(new IntValue(1))))))),
                                new PrintStatement(new ArithmeticExpression(3, new VariableExpression("v"),
                                        new ValueExpression(new IntValue(10)))))));

        ProgramState program13 = new ProgramState(exeStack13, symTable13, output13, fileTable13, heap13, cyclicBarrier13, latchTable13, lockTable13, semaphoreTable13, example13);
        repository13 = new Repository(program13, path + "log13.txt");
        controller13 = new Controller(repository13);

        MyStack<IStatement> exeStack14 = new MyStack<>();
        MyDictionary<String, Value> symTable14 = new MyDictionary<>();
        MyList<Value> output14 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable14 = new MyDictionary<>();
        MyHeap<Value> heap14 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier14 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable14 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable14 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable14 = new Semaphore<>();

        example14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"),
                        new ValueExpression(new IntValue(3)), "<"), new CompoundStatement(new ForkStatement(
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new AssignmentStatement("v", new ArithmeticExpression(1,
                                        new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                        new AssignmentStatement("v", new ArithmeticExpression(1,
                                new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                        new CompoundStatement(new SleepStatement(5), new PrintStatement(new ArithmeticExpression(
                                3, new VariableExpression("v"), new ValueExpression(new IntValue(10))
                        ))))));

        ProgramState program14 = new ProgramState(exeStack14, symTable14, output14, fileTable14, heap14, cyclicBarrier14, latchTable14, lockTable14, semaphoreTable14, example14);
        repository14 = new Repository(program14, path + "log14.txt");
        controller14 = new Controller(repository14);

        MyStack<IStatement> exeStack15 = new MyStack<>();
        MyDictionary<String, Value> symTable15 = new MyDictionary<>();
        MyList<Value> output15 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable15 = new MyDictionary<>();
        MyHeap<Value> heap15 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier15 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable15 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable15 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable15 = new Semaphore<>();

        example15 = new CompoundStatement(new VariableDeclarationStatement("v1", new IntType()),
                new CompoundStatement(new AssignmentStatement("v1", new ValueExpression(new IntValue(2))),
                        new CompoundStatement(new VariableDeclarationStatement("v2", new IntType()),
                                new CompoundStatement(new AssignmentStatement("v2", new ValueExpression(new IntValue(3))),
                                        new IfStatement(new RelationalExpression(new VariableExpression("v1"),
                                                new ValueExpression(new IntValue(0)), "!="),
                                                new PrintStatement(new MUL(new VariableExpression("v1"),
                                                        new VariableExpression("v2"))),
                                                new PrintStatement(new VariableExpression("v1")))))));

        ProgramState program15 = new ProgramState(exeStack15, symTable15, output15, fileTable15, heap15, cyclicBarrier15, latchTable15, lockTable15, semaphoreTable15, example15);
        repository15 = new Repository(program15, path + "log15.txt");
        controller15 = new Controller(repository15);

        MyStack<IStatement> exeStack16 = new MyStack<>();
        MyDictionary<String, Value> symTable16 = new MyDictionary<>();
        MyList<Value> output16 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable16 = new MyDictionary<>();
        MyHeap<Value> heap16 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier16 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable16 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable16 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable16 = new Semaphore<>();

        example16 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompoundStatement(new SwitchStatement(
                                                                new ArithmeticExpression(3, new VariableExpression("a"),
                                                                        new ValueExpression(new IntValue(10))),
                                                                new ArithmeticExpression(3, new VariableExpression("b"),
                                                                        new VariableExpression("c")),
                                                                new ValueExpression(new IntValue(10)),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                                        new PrintStatement(new VariableExpression("b"))),
                                                                new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                        new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))),
                                                                new PrintStatement(new ValueExpression(new IntValue(300))))))))));

        ProgramState program16 = new ProgramState(exeStack16, symTable16, output16, fileTable16, heap16, cyclicBarrier16, latchTable16, lockTable16, semaphoreTable16, example16);
        repository16 = new Repository(program16, path + "log16.txt");
        controller16 = new Controller(repository16);

        MyStack<IStatement> exeStack17 = new MyStack<>();
        MyDictionary<String, Value> symTable17 = new MyDictionary<>();
        MyList<Value> output17 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable17 = new MyDictionary<>();
        MyHeap<Value> heap17 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier17 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable17 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable17 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable17 = new Semaphore<>();

        example17 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new RepeatStatement(new CompoundStatement(
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression(2, new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new AssignmentStatement("v", new ArithmeticExpression(1, new VariableExpression("v"), new ValueExpression(new IntValue(1))))),
                                new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "==")),
                                new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                                        new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
                                                new CompoundStatement(new VariableDeclarationStatement("z", new IntType()),
                                                        new CompoundStatement(new VariableDeclarationStatement("w", new IntType()),
                                                                new CompoundStatement(new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
                                                                        new CompoundStatement(new AssignmentStatement("y", new ValueExpression(new IntValue(2))),
                                                                                new CompoundStatement(new AssignmentStatement("z", new ValueExpression(new IntValue(3))),
                                                                                        new CompoundStatement(new AssignmentStatement("w", new ValueExpression(new IntValue(4))),
                                                                                                new PrintStatement(new ArithmeticExpression(3, new VariableExpression("v"), new ValueExpression(new IntValue(10)))))))))))))));

        ProgramState program17 = new ProgramState(exeStack17, symTable17, output17, fileTable17, heap17, cyclicBarrier17, latchTable17, lockTable17, semaphoreTable17, example17);
        repository17 = new Repository(program17, path + "log17.txt");
        controller17 = new Controller(repository17);

        MyStack<IStatement> exeStack18 = new MyStack<>();
        MyDictionary<String, Value> symTable18 = new MyDictionary<>();
        MyList<Value> output18 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable18 = new MyDictionary<>();
        MyHeap<Value> heap18 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier18 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable18 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable18 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable18 = new Semaphore<>();

        example18 = new CompoundStatement(new VariableDeclarationStatement("b", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                        new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new ConditionalAssignment("c", new VariableExpression("b"),
                                        new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("c")),
                                                new CompoundStatement(new ConditionalAssignment("c", new ValueExpression(new BoolValue(false)),
                                                        new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                        new PrintStatement(new VariableExpression("c"))))))));

        ProgramState program18 = new ProgramState(exeStack18, symTable18, output18, fileTable18, heap18, cyclicBarrier18, latchTable18, lockTable18, semaphoreTable18, example18);
        repository18 = new Repository(program18, path + "log18.txt");
        controller18 = new Controller(repository18);

        MyStack<IStatement> exeStack19 = new MyStack<>();
        MyDictionary<String, Value> symTable19 = new MyDictionary<>();
        MyList<Value> output19 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable19 = new MyDictionary<>();
        MyHeap<Value> heap19 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier19 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable19 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable19 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable19 = new Semaphore<>();

        example19 = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new RefType(new IntType())),
                                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                                        new CompoundStatement(new HeapAllocation("v1", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new HeapAllocation("v2", new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new HeapAllocation("v3", new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewCyclicBarrier("cnt", new HeapReading(new VariableExpression("v2"))),
                                                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new AwaitCyclicBarrier("cnt"),
                                                                                new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression(3,
                                                                                        new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                        new PrintStatement(new HeapReading(new VariableExpression("v1")))))),
                                                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new AwaitCyclicBarrier("cnt"),
                                                                                        new CompoundStatement(new HeapWriting("v2", new ArithmeticExpression(3,
                                                                                                new HeapReading(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                new CompoundStatement(new HeapWriting("v2", new ArithmeticExpression(3,
                                                                                                        new HeapReading(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                        new PrintStatement(new HeapReading(new VariableExpression("v2"))))))),
                                                                                        new CompoundStatement(new AwaitCyclicBarrier("cnt"), new PrintStatement(new HeapReading(new VariableExpression("v3"))))))))))))));

        ProgramState program19 = new ProgramState(exeStack19, symTable19, output19, fileTable19, heap19, cyclicBarrier19, latchTable19, lockTable19, semaphoreTable19, example19);
        repository19 = new Repository(program19, path + "log19.txt");
        controller19 = new Controller(repository19);

        MyStack<IStatement> exeStack20 = new MyStack<>();
        MyDictionary<String, Value> symTable20 = new MyDictionary<>();
        MyList<Value> output20 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable20 = new MyDictionary<>();
        MyHeap<Value> heap20 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier20 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable20 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable20 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable20 = new Semaphore<>();

        example20 = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new RefType(new IntType())),
                                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                                        new CompoundStatement(new HeapAllocation("v1", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new HeapAllocation("v2", new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new HeapAllocation("v3", new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewLatch("cnt", new HeapReading(new VariableExpression("v2"))),
                                                                        new CompoundStatement(new ForkStatement(
                                                                                new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression(3, new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v1"))),
                                                                                                new CompoundStatement(new CountDownLatch("cnt"),
                                                                                                        new ForkStatement(
                                                                                                                new CompoundStatement(new HeapWriting("v2", new ArithmeticExpression(3, new HeapReading(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v2"))),
                                                                                                                                new CompoundStatement(new CountDownLatch("cnt"),
                                                                                                                                        new ForkStatement(
                                                                                                                                                new CompoundStatement(new HeapWriting("v3", new ArithmeticExpression(3, new HeapReading(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))),
                                                                                                                                                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v3"))),
                                                                                                                                                                new CountDownLatch("cnt")))
                                                                                                                                        ))))
                                                                                                        ))))),
                                                                                new CompoundStatement(new AwaitLatch("cnt"),
                                                                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                new CompoundStatement(new CountDownLatch("cnt"),
                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))))))))))))));

        ProgramState program20 = new ProgramState(exeStack20, symTable20, output20, fileTable20, heap20, cyclicBarrier20, latchTable20, lockTable20, semaphoreTable20, example20);
        repository20 = new Repository(program20, path + "log20.txt");
        controller20 = new Controller(repository20);

        MyStack<IStatement> exeStack21 = new MyStack<>();
        MyDictionary<String, Value> symTable21 = new MyDictionary<>();
        MyList<Value> output21 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable21 = new MyDictionary<>();
        MyHeap<Value> heap21 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier21 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable21 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable21 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable21 = new Semaphore<>();

        example21 = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                                new CompoundStatement(new VariableDeclarationStatement("q", new IntType()),
                                        new CompoundStatement(new HeapAllocation("v1", new ValueExpression(new IntValue(20))),
                                                new CompoundStatement(new HeapAllocation("v2", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(new NewLock("x"),
                                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("x"),
                                                                        new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression(2, new HeapReading(
                                                                                new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                                                new UnlockStatement("x")))),
                                                                        new CompoundStatement(new LockStatement("x"),
                                                                                new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression(3, new HeapReading(
                                                                                        new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                        new UnlockStatement("x"))))),
                                                                        new CompoundStatement(new NewLock("q"),
                                                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("q"),
                                                                                        new CompoundStatement(new HeapWriting("v2", new ArithmeticExpression(1, new HeapReading(
                                                                                                new VariableExpression("v2")), new ValueExpression(new IntValue(5)))),
                                                                                                new UnlockStatement("q")))),
                                                                                        new CompoundStatement(new LockStatement("q"),
                                                                                                new CompoundStatement(new HeapWriting("v2", new ArithmeticExpression(3, new HeapReading(
                                                                                                        new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                        new UnlockStatement("q"))))),
                                                                                        new CompoundStatement(new NopStatement(),
                                                                                                new CompoundStatement(new NopStatement(),
                                                                                                        new CompoundStatement(new NopStatement(),
                                                                                                                new CompoundStatement(new NopStatement(),
                                                                                                                        new CompoundStatement(new LockStatement("x"),
                                                                                                                                new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v1"))),
                                                                                                                                        new CompoundStatement(new UnlockStatement("x"),
                                                                                                                                                new CompoundStatement(new LockStatement("q"),
                                                                                                                                                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v2"))),
                                                                                                                                                                new UnlockStatement("q"))))))))))))))))))));

        ProgramState program21 = new ProgramState(exeStack21, symTable21, output21, fileTable21, heap21, cyclicBarrier21, latchTable21, lockTable21, semaphoreTable21, example21);
        repository21 = new Repository(program21, path + "log21.txt");
        controller21 = new Controller(repository21);

        MyStack<IStatement> exeStack22 = new MyStack<>();
        MyDictionary<String, Value> symTable22 = new MyDictionary<>();
        MyList<Value> output22 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable22 = new MyDictionary<>();
        MyHeap<Value> heap22 = new MyHeap<>();
        CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier22 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable22 = new LatchTable<>();
        LockTable<Integer, Integer> lockTable22 = new LockTable<>();
        Semaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable22 = new Semaphore<>();

        example22 = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                        new CompoundStatement(new HeapAllocation("v1", new ValueExpression(new IntValue(1))),
                                new CompoundStatement(new NewSemaphore("cnt", new HeapReading(new VariableExpression("v1"))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireStatement("cnt"),
                                                new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression(3,
                                                        new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v1"))),
                                                                new ReleaseStatement("cnt"))))),
                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireStatement("cnt"),
                                                        new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression(3,
                                                                new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression(3,
                                                                        new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(2)))),
                                                                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v1"))),
                                                                                new ReleaseStatement("cnt")))))),
                                                        new CompoundStatement(new AcquireStatement("cnt"),
                                                                new CompoundStatement(new PrintStatement(new ArithmeticExpression(2,
                                                                        new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                                        new ReleaseStatement("cnt")))))))));

        ProgramState program22 = new ProgramState(exeStack22, symTable22, output22, fileTable22, heap22, cyclicBarrier22, latchTable22, lockTable22, semaphoreTable22, example22);
        repository22 = new Repository(program22, path + "log22.txt");
        controller22 = new Controller(repository22);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.setUp();
        ObservableList<Controller> myData = FXCollections.observableArrayList();
        myData.add(controller1);
        myData.add(controller2);
        myData.add(controller3);
        myData.add(controller4);
        myData.add(controller5);
        myData.add(controller6);
        myData.add(controller7);
        myData.add(controller8);
        myData.add(controller9);
        myData.add(controller10);
        myData.add(controller11);
        myData.add(controller12);
        myData.add(controller13);
        myData.add(controller14);
        myData.add(controller15);
        myData.add(controller16);
        myData.add(controller17);
        myData.add(controller18);
        myData.add(controller19);
        myData.add(controller20);
        myData.add(controller21);
        myData.add(controller22);
        ProgramsListView.setItems(myData);
        ProgramsListView.getSelectionModel().selectFirst();
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean ok = true;
                try{
                    ProgramsListView.getSelectionModel().getSelectedItem().typecheck();
                } catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("type check error");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    ok = false;
                }
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == PrgRunController.class) {
                        return new PrgRunController(ProgramsListView.getSelectionModel().getSelectedItem());
                    }
                    else{
                        try{
                            return type.newInstance();
                        } catch (Exception ex){
                            System.err.println("Could not create controller for "+type.getName());
                            throw new RuntimeException(ex);
                        }
                    }
                };
                if (ok) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProgramLayout.fxml"));
                        fxmlLoader.setControllerFactory(controllerFactory);
                        programRoot = fxmlLoader.load();
                        Scene programScene = new Scene(programRoot);
                        programStage.setTitle("ToyLanguage");
                        programStage.setScene(programScene);
                        programStage.show();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
    }

}