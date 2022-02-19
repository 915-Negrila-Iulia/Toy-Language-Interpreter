package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.IList;
import app.Model.ADT.MyDictionary;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.Type;
import app.Model.ToyValue.Value;

public class PrintStatement implements IStatement{

    /*
        PrintStatement class implements abstract methods of IStatement interface
     */

    private final Expression expression;

    public PrintStatement(Expression exp){
        /*
            Parametrised constructor which creates a PrintStatement object
            :param expr: expression to be printed in the OutputTable (Expression type)
         */

        this.expression = exp;
    }

    @Override
    public IStatement deepCopy(){
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "print("+expression.toString()+")";
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception {
        /*
            Evaluates a given expression and adds the result to the OutputTable
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String,Value> table = state.getSymbolTable();
        IList<Value> output = state.getOutputList();
        IHeap<Value> heap = state.getHeap();

        try {
            output.add(expression.eval(table,heap));
        }catch(Exception e){
            throw new Exception(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        expression.typecheck(typeEnv);
        return typeEnv;
    };

}
