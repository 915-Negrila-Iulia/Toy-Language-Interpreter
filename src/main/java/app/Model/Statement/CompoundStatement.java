package app.Model.Statement;

import app.Model.ADT.IStack;
import app.Model.ADT.MyDictionary;
import app.Model.ProgramState;
import app.Model.ToyType.Type;

public class CompoundStatement implements IStatement{

    /*
        CompoundStatement class implements abstract methods of IStatement interface
     */

    private final IStatement first;
    private final IStatement second;

    public CompoundStatement(IStatement first, IStatement second){
        /*
            Parametrised constructor which creates an CompoundStatement object
            :param first: statement to be pushed on ExecutionStack (IStatement type)
            :param second: statement to be pushed on ExecutionStack (IStatement type)
         */

        this.first = first;
        this.second = second;
    }

    public IStatement getFirst(){
        return first;
    };
    public IStatement getSecond(){
        return second;
    };

    @Override
    public IStatement deepCopy(){
        return new CompoundStatement(first.deepCopy(),second.deepCopy());
    }

    @Override
    public String toString(){

        return first.toString()+"; "+second.toString();
    }

    @Override
    public ProgramState execute (ProgramState state){
        /*
            Break a compound statement in two simple statements and
            push the second statement on the ExecutionStack and then push the first statement too
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks correctness of statements
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: new TypeEnvironment dictionary
         */

        MyDictionary<String,Type> typeEnv1 = first.typecheck(typeEnv);
        MyDictionary<String,Type> typeEnv2 = second.typecheck(typeEnv1);
        return typeEnv2;
    };

}
