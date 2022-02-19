package app.Model.Statement;

import app.Model.ADT.MyDictionary;
import app.Model.ProgramState;
import app.Model.ToyType.Type;

public class NopStatement implements IStatement{

    /*
        NopStatement class implements abstract methods of IStatement interface
     */

    public NopStatement(){

    }

    @Override
    public IStatement deepCopy(){
        return new NopStatement();
    }

    @Override
    public String toString() {
        return "no operation";
    }

    @Override
    public ProgramState execute (ProgramState state){
        /*
            Performs no operation
            :param state: state of the program (ProgramState type)
            :return: null
         */
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */
        return typeEnv;
    };

}
