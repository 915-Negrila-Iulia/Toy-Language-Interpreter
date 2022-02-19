package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotRefException;
import app.Model.Exception.TypesDontMatchException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyType.RefType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.RefValue;
import app.Model.ToyValue.Value;

public class HeapAllocation implements IStatement{

    /*
        HeapAllocation class implements abstract methods of IStatement interface
     */

    private final String id;
    private final Expression expr;

    public HeapAllocation(String id, Expression expr){
        /*
            Parametrised constructor which creates a HeapAllocation object
            :param id: name of a variable (String type)
            :param expr: expression having the value associated to the address of 'id' in the HeapTable (Expression type)
         */

        this.id = id;
        this.expr = expr;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Allocates memory for a variable in HeapTable
            Creates a new free address in HeapTable which is associated to value of expression 'expr'
            Updates the RefValue associated to 'id' from SymbolTable such that it has the address and location type
            the same as the new free address created in the HeapTable
            If the variable 'id' is not defined in SymbolTable or if it's not RefType a custom Expression is thrown
            In case the types of 'id' and 'expr' do not match a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String, Value> symTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        if(symTable.isDefined(id)){
            Value id_value = symTable.lookUp(id);

            if(id_value.getType() instanceof RefType) {
                RefValue id_ref = (RefValue) id_value;
                Value expr_value = expr.eval(symTable,heap);
                if (expr_value.getType().equals(id_ref.getLocationType())) {
                    heap.add(expr_value);
                    RefValue new_ref_value = new RefValue(
                            heap.getLocationOfLastAdded(),
                            expr_value.getType());
                    symTable.update(id, new_ref_value);
                } else
                    throw new TypesDontMatchException("type of " + expr_value + " and type of " + id_ref + " do not match");
            }else
                throw new NotRefException(id+" does not have type RefType");
        }else
            throw new VariableNotDefinedException(id+" is not defined");

        return null;
    };

    @Override
    public IStatement deepCopy(){
        return new HeapAllocation(id,expr.deepCopy());
    };

    @Override
    public String toString(){
        return "new("+id+","+expr.toString()+")";
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
        Type type_expr = expr.typecheck(typeEnv);
        if(type_variable.equals(new RefType(type_expr)))
            return typeEnv;
        else
            throw new TypesDontMatchException("allocation stmt: right hand side and left hand side have different types");
    };
}
