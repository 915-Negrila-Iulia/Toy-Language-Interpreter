package app.Model.ToyExpression;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.DivisionByZeroException;
import app.Model.Exception.InvalidIntOperatorException;
import app.Model.Exception.NotIntegerException;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

public class ArithmeticExpression implements Expression{

    /*
        ArithmeticExpression class implements abstract methods of Expression interface
     */

    private final Expression expr1;
    private final Expression expr2;
    private final int operator;

    public ArithmeticExpression(int op, Expression e1, Expression e2){
        /*
            Parametrised constructor which creates an ArithmeticExpression object
            :param op: operator of the arithmetic expression (int type); 1=plus, 2=minus, 3=star, 4=divide
            :param e1: first operand in the arithmetic expression (Expression type)
            :param e2: second operand in the arithmetic expression (Expression type)
         */

        this.expr1 = e1;
        this.expr2 = e2;
        this.operator = op;
    }

    @Override
    public Expression deepCopy(){
        return new ArithmeticExpression(operator,expr1.deepCopy(),expr2.deepCopy());
    }

    @Override
    public String toString(){
        String op="";
        if(operator==1)
            op="+";
        else if(operator==2)
            op="-";
        else if(operator==3)
            op="*";
        else if(operator==4)
            op="/";
        return expr1.toString()+op+expr2.toString();
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws Exception {
        /*
            Gets the values of the operands and performs the given operation on them
            If an operand is not integer or the operation is invalid a custom Expression is thrown
            In case of division by zero, a custom Expression is thrown
            :param table: reference to the SymbolTable (IDictionary)
            :param heap: reference to the HeapTable (IHeap)
            :return: value of an arithmetic expression (Value type)
         */

        Value value1, value2;
        try {
            value1 = expr1.eval(table,heap);

            if (value1.getType().equals(new IntType())) {
                value2 = expr2.eval(table,heap);

                if (value2.getType().equals(new IntType())) {
                    IntValue intValue1 = (IntValue) value1;
                    IntValue intValue2 = (IntValue) value2;

                    int number1, number2;
                    number1 = intValue1.getValue();
                    number2 = intValue2.getValue();

                    if (operator == 1)
                        return new IntValue(number1 + number2);
                    else if (operator == 2)
                        return new IntValue(number1 - number2);
                    else if (operator == 3)
                        return new IntValue(number1 * number2);
                    else if (operator == 4) {
                        if (number2 != 0)
                            return new IntValue(number1 / number2);
                        else
                            throw new DivisionByZeroException("division by zero");
                    } else
                        throw new InvalidIntOperatorException("invalid arithmetic operator");
                } else {
                    throw new NotIntegerException(value2 + " is not an integer");
                }
            } else {
                throw new NotIntegerException(value1 + " is not an integer");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage(),e);
        }
    }

    @Override
    public Type typecheck(MyDictionary<String,Type> typeEnv) throws Exception{
        /*
            Checks if both operands are of type IntType
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: new instance of IntType
         */

        Type type1, type2;
        type1 = expr1.typecheck(typeEnv);
        type2 = expr2.typecheck(typeEnv);

        if(type1.equals(new IntType()))
            if(type2.equals(new IntType()))
                return new IntType();
            else
                throw new NotIntegerException("second operand is not integer");
        else
            throw new NotIntegerException("first operand is not integer");
    };

}
