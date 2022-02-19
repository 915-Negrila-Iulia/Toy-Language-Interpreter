package app.Model.ToyExpression;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotRefException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ToyType.RefType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.RefValue;
import app.Model.ToyValue.Value;

public class HeapReading implements Expression{

    /*
        HeapReading class implements abstract methods of Expression interface
     */

    private final Expression expr;

    public HeapReading(Expression expr){
        /*
            Parametrised constructor which creates an HeapReading object
            :param expr: expression to be read from heap (Expression type)
         */

        this.expr = expr;
    }

    @Override
    public String toString() {
        return "rH("+expr.toString()+")";
    }

    @Override
    public Value eval(IDictionary<String,Value> table, IHeap<Value> heap) throws Exception{
        /*
            Gets the RefValue of the expression and the value associated to RefValue's address in the heap
            If the value of expression is not RefValue a custom Expression is thrown
            Checks if address of the value is defined in heap, if not throws a custom Exception
            :param table: reference to the SymbolTable (IDictionary)
            :param heap: reference to the HeapTable (IHeap)
            :return: value from an address in the heap (Value type)
         */

        Value value = expr.eval(table, heap);

        if(value instanceof RefValue){
            RefValue ref_value = (RefValue) value;

            if(heap.isDefined(ref_value.getAddress())){
                int address = ref_value.getAddress();
                Value associated_value = heap.lookUp(address);

                return associated_value;
            }
            else
                throw new VariableNotDefinedException("address not found");
        }
        else
            throw new NotRefException(value.toString()+" is not RefValue");

    };

    @Override
    public Expression deepCopy(){
        return new HeapReading(expr.deepCopy());
    };

    @Override
    public Type typecheck(MyDictionary<String,Type> typeEnv) throws Exception{
        /*
            Checks if expression is of type RefType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: inner type of the current RefType
         */

        Type type;
        type = expr.typecheck(typeEnv);
        if(type instanceof RefType){
            RefType ref_type = (RefType) type;
            return ref_type.getInner();
        }
        else
            throw new NotRefException("the rH argument is not a RefType");
    };

}
