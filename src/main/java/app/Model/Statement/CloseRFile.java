package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotStringException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.StringType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.StringValue;
import app.Model.ToyValue.Value;

import java.io.BufferedReader;

public class CloseRFile implements IStatement{

    /*
        CloseRFile class implements abstract methods of IStatement interface
     */

    private final Expression expr;

    public CloseRFile(Expression expr){
        /*
            Parametrised constructor which creates a CloseRFile object
            :param expr: expression having the value associated to a file that will be closed (Expression type)
         */
        this.expr = expr;
    }

    @Override
    public IStatement deepCopy(){
        return new CloseRFile(expr.deepCopy());
    }

    @Override
    public String toString(){
        return expr.toString()+".close()";
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Closes a file
            Removes the value of the expression 'expr' (which is the name of the file) from the FileTable
            If the type of the value computed is not StringType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Value> heap = state.getHeap();

        try {
            Value value = expr.eval(symbolTable,heap);
            if (value.getType().equals(new StringType())) {
                StringValue fileName = (StringValue) value;
                BufferedReader bufReader = fileTable.lookUp(fileName);
                bufReader.close();
                fileTable.remove(fileName);

            } else {
                throw new NotStringException(value + " is not of type string");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage(),e);
        }

        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if type of expression 'expr' is StringType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_expr = expr.typecheck(typeEnv);
        if(type_expr.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new NotStringException("name of file is not String type");

    };
}
