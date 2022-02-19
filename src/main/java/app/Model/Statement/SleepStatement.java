package app.Model.Statement;


import app.Model.ADT.IStack;
import app.Model.ADT.MyDictionary;
import app.Model.ProgramState;
import app.Model.ToyType.Type;

public class SleepStatement implements IStatement{

    /*
        SleepStatement class implements abstract methods of IStatement interface
     */

    private final int number;

    public SleepStatement(int number){
        /*
            Parametrised constructor which creates a SleepStatement object
            :param number: how long to make the program sleep (int type)
         */

        this.number = number;
    }

    @Override
    public String toString(){
        return "sleep("+number+")";
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Simulates Sleep statement
            Pushes on stack 'number' new instances of NopStatement (do no operation)
            If the number is not positive an Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        if(number>=0){
            IStack<IStatement> stack = state.getStack();
            if(number == 0)
                stack.push(new NopStatement());
            else
                stack.push(new SleepStatement(number-1));
        }
        else throw new Exception(number+" must be positive");
        return null;
    };

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        return typeEnv;
    };

    @Override
    public IStatement deepCopy(){
        return new SleepStatement(number);
    };
}
