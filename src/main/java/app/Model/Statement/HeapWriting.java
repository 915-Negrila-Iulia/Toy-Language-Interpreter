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

public class HeapWriting implements IStatement{

    /*
        HeapWriting class implements abstract methods of IStatement interface
     */

    private final String id;
    private final Expression expr;

    public HeapWriting(String id, Expression expr){
        /*
            Parametrised constructor which creates a HeapWriting object
            :param id: name of a variable (String type)
            :param expr: expression having the value associated to variable with 'id' name (Expression type)
         */

        this.id =id;
        this.expr=expr;
    }

    @Override
    public String toString(){
        return "wH("+id+","+expr.toString()+")";
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception {
        /*
            Stores a value of a variable at its address in HeapTable
            Access the HeapTable using the address from 'id' and that Heap entry is updated
            to the result of the expression 'expr' evaluation
            If the variable 'id' is not defined in SymbolTable or if it's not RefType a custom Expression is thrown
            In case the types of 'id' and 'expr' do not match a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IDictionary<String, Value> symTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        if(symTable.isDefined(id)){
            Value value = symTable.lookUp(id);

            if(value.getType() instanceof RefType){
                RefValue ref_value = (RefValue) value;
                int address = ref_value.getAddress();

                if(heap.isDefined(address)){
                    Value expr_value = expr.eval(symTable,heap);

                    if(expr_value.getType().equals(ref_value.getLocationType())){
                        heap.update(address,expr_value);
                    }
                    else
                        throw new TypesDontMatchException(expr_value+" and "+ref_value+" types do not match");
                }
                else
                    throw new VariableNotDefinedException("address not found");
            }
            else
                throw new NotRefException(id +" does not have type RefType");
        }
        else
            throw new VariableNotDefinedException(id +" is not defined");

        return null;
    };

    @Override
    public IStatement deepCopy(){return new HeapWriting(id,expr.deepCopy());};

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
            throw new TypesDontMatchException("write heap stmt: right hand side and left hand side have different types");
    };
}
