package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.TypesDontMatchException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.Type;
import app.Model.ToyValue.Value;

public class AssignmentStatement implements IStatement{

    /*
        AssignmentStatement class implements abstract methods of IStatement interface
     */

    private final String id;
    private final Expression expr;

    public AssignmentStatement(String id, Expression expr){
        /*
            Parametrised constructor which creates an AssignmentStatement object
            :param id: name of a variable (String type)
            :param expr: expression having the value associated to variable with 'id' name (Expression type)
         */

        this.id = id;
        this.expr = expr;
    }

    @Override
    public IStatement deepCopy(){
        return new AssignmentStatement(id,expr.deepCopy());
    }

    @Override
    public String toString(){
        return id+"="+expr.toString();
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Assigns a value to a variable
            Adds in the SymbolTable the name of a variable ('id') and its associated value (value of expression 'expr')
            If the variable is not defined in SymbolTable a custom Expression is thrown
            In case the types of 'id' and 'expr' do not match a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String, Value> table = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        try{
            if(table.isDefined(id)){

                Value val = expr.eval(table,heap);
                Type typeId = (table.lookUp(id)).getType();
                if (val.getType().equals(typeId)) {
                    table.add(id, val);
                }else{
                    throw new TypesDontMatchException("types of "+id+" and "+expr+" do not match");
                }

            }else{
                throw new VariableNotDefinedException(id+" is not defined");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if type of 'id' and type of 'expr' are the same
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_variable = typeEnv.lookUp(id);
        Type type_expression = expr.typecheck(typeEnv);
        if(type_variable.equals(type_expression))
            return typeEnv;
        else
            throw new TypesDontMatchException("Assignment: right hand side and left hand side have different types ");
    };

}
