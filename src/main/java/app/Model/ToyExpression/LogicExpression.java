package app.Model.ToyExpression;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.InvalidBoolOperatorException;
import app.Model.Exception.NotBoolException;
import app.Model.ToyType.BoolType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.BoolValue;
import app.Model.ToyValue.Value;

public class LogicExpression implements Expression{

    /*
        LogicExpression class implements abstract methods of Expression interface
     */

    private final Expression expr1;
    private final Expression expr2;
    private final int operator;

    public LogicExpression(int op, Expression e1, Expression e2){
        /*
            Parametrised constructor which creates a LogicExpression object
            :param op: operator of the logic expression (int type); 1=and, 2=or
            :param e1: first operand in the logic expression (Expression type)
            :param e2: second operand in the logic expression (Expression type)
         */

        this.expr1 = e1;
        this.expr2 = e2;
        this.operator = op;
    }

    @Override
    public Expression deepCopy(){
        return new LogicExpression(operator,expr1.deepCopy(),expr2.deepCopy());
    }

    @Override
    public String toString(){
        String op="";
        if(operator==1)
            op=" AND ";
        else if(operator==2)
            op=" OR ";
        return expr1.toString()+op+expr2.toString();
    }

    @Override
    public Value eval(IDictionary<String,Value> table, IHeap<Value> heap) throws Exception{
        /*
            Gets the values of the operands and performs the given operation on them
            If an operand is not boolean or the operation is invalid a custom Expression is thrown
            :param table: reference to the SymbolTable (IDictionary)
            :param heap: reference to the HeapTable (IHeap)
            :return: value of a logic expression (Value type)
         */

        Value value1, value2;
        try {
            value1 = expr1.eval(table, heap);

            if (value1.getType().equals(new BoolType())) {
                value2 = expr2.eval(table, heap);


                if (value2.getType().equals(new BoolType())) {

                    BoolValue boolValue1 = (BoolValue) value1;
                    BoolValue boolValue2 = (BoolValue) value2;

                    boolean bool1, bool2;
                    bool1 = boolValue1.getValue();
                    bool2 = boolValue2.getValue();

                    if (operator == 1)
                        return new BoolValue(bool1 && bool2);
                    else if (operator == 2)
                        return new BoolValue(bool1 || bool2);
                    else
                        throw new InvalidBoolOperatorException("invalid logic operator");

                } else {
                    throw new NotBoolException(value2 + " is not boolean");
                }
            } else {
                throw new NotBoolException(value1 + " is not boolean");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage(),e);
        }
    };

    @Override
    public Type typecheck(MyDictionary<String,Type> typeEnv) throws Exception{
        /*
            Checks if both operands are of type BoolType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: new instance of BoolType
         */

        Type type1, type2;
        type1 = expr1.typecheck(typeEnv);
        type2 = expr2.typecheck(typeEnv);

        if(type1.equals(new BoolType()))
            if(type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new NotBoolException("second operand is not boolean");
        else
            throw new NotBoolException("first operand is not boolean");
    };

}
