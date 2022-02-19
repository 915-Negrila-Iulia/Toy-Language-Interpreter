package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotIntegerException;
import app.Model.Exception.NotStringException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.IntType;
import app.Model.ToyType.StringType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.StringValue;
import app.Model.ToyValue.Value;

import java.io.BufferedReader;

public class ReadFile implements IStatement{

    /*
        ReadFile class implements abstract methods of IStatement interface
     */

    private final Expression expr;
    private final String id;

    public ReadFile(Expression expr, String id){
        /*
            Parametrised constructor which creates a ReadFile object
            :param id: name of a variable (String type)
            :param expr: expression having the value associated to a BufferReader in the FileTable (Expression type)
         */

        this.id = id;
        this.expr = expr;
    }

    @Override
    public IStatement deepCopy(){
        return new ReadFile(expr.deepCopy(),id);
    }

    @Override
    public String toString(){
        return id+".read()";
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Reads a line from a file having a value which is given to variable 'id'
            If the name of the file does not exist (value of 'expr' is not defined in FileTable) a custom Expression is thrown
            If the type of the value computed is not StringType a custom Expression is thrown
            If the type of the variable 'id' is not IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */


        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Value> heap = state.getHeap();

        if(symbolTable.isDefined(id)){

            Value valueId = symbolTable.lookUp(id);
            if(valueId.getType().equals(new IntType())){
                Value value = expr.eval(symbolTable,heap);

                if(value.getType().equals(new StringType())){
                    StringValue fileName = (StringValue) value;
                    BufferedReader bufferReader = fileTable.lookUp(fileName);
                    String line = bufferReader.readLine();
                    Value valueFromFile;

                    if(line==null){
                        valueFromFile = new IntType().defaultValue();
                    }
                    else{
                        int number = Integer.parseInt(line);
                        valueFromFile = new IntValue(number);
                    }

                    symbolTable.update(id,valueFromFile);

                } else{
                    throw new NotStringException(value +" is not of type string");
                }
            } else{
                throw new NotIntegerException(id+" is not of type int");
            }
        }else{
            throw new VariableNotDefinedException(id+" is not defined");
        }

        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if type of expression 'expr' is StringType
            Checks if type of variable with name 'id' is IntType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_expr = expr.typecheck(typeEnv);
        if(type_expr.equals(new StringType())) {
            Type type_variable = typeEnv.lookUp(id);
            if(type_variable.equals(new IntType())){
                return typeEnv;
            }
            else
                throw new NotIntegerException("variable to read into is not Integer");
        }
        else
            throw new NotStringException("name of file is not String type");
    };

}
