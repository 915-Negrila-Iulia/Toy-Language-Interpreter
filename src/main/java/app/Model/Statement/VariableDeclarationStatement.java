package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.AlreadyDeclaredException;
import app.Model.ProgramState;
import app.Model.ToyType.*;
import app.Model.ToyValue.Value;
import app.Model.ToyType.Type;
import com.sun.jdi.InvalidTypeException;

public class VariableDeclarationStatement implements IStatement{

    /*
        VariableDeclarationStatement class implements abstract methods of IStatement interface
     */

    private final String id;
    private final Type type;

    public VariableDeclarationStatement(String id, Type type){
        /*
            Parametrised constructor which creates a VariableDeclarationStatement object
            :param id: name of a variable (String type)
            :param type: type of variable with 'id' name ('Type' type)
         */

        this.id = id;
        this.type = type;
    }

    @Override
    public IStatement deepCopy(){
        return new VariableDeclarationStatement(id,type.deepCopy());
    }

    @Override
    public String toString(){

        return type+" "+id;
    }

    @Override
    public ProgramState execute (ProgramState state) throws AlreadyDeclaredException, InvalidTypeException{
        /*
            Declares a new variable by adding it to the symbolTable
            If the variable 'id' is already defined in SymbolTable a custom Expression is thrown
            In case the 'type' is not a valid Type a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String, Value> table = state.getSymbolTable();

        if(table.isDefined(id)){
            throw new AlreadyDeclaredException(id+" is already declared");
        }
        else{
            if(type.equals(new IntType())){
                // default value is 0
                Value intValue = new IntType().defaultValue();
                table.add(id,intValue);
            }
            else if(type.equals(new BoolType())){
                // default value is false
                Value boolValue = new BoolType().defaultValue();
                table.add(id,boolValue);
            }
            else if(type.equals(new StringType())){
                // default value is the empty string
                Value stringValue = new StringType().defaultValue();
                table.add(id,stringValue);
            }
            else if(type instanceof RefType){
                RefType ref_type = (RefType) type;
                Value refValue = new RefType(ref_type.getInner()).defaultValue();
                table.add(id,refValue);
            }
            else{
                throw new InvalidTypeException(type+" is not a valid type");
            }
        }
        return null;
    }

    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
             Adds the new pair (id,type) in the TypeEnvironment dictionary
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: updated TypeEnvironment dictionary
         */

        typeEnv.add(id,type);
        return typeEnv;
    };

}

