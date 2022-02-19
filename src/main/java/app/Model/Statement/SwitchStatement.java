package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.IStack;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotIntegerException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyExpression.RelationalExpression;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.Value;

public class SwitchStatement implements IStatement{

    /*
        SwitchStatement class implements abstract methods of IStatement interface
     */

    private final Expression expr;
    private final Expression expr1;
    private final Expression expr2;
    private final IStatement stmt1;
    private final IStatement stmt2;
    private final IStatement stmt3;

    public SwitchStatement(Expression e, Expression e1, Expression e2, IStatement s1, IStatement s2, IStatement s3){
        /*
             Parametrised constructor which creates a SwitchStatement object
            :param expr: expression to be compared (Expression type)
            :param expr1: first case expression (Expression type)
            :param exp2: second case expression (Expression type)
            :param stmt1: statement to execute if only first case is true (IStatement type)
            :param stmt2: statement to execute if only second case is true (IStatement type)
            :param stmt3: statement to execute by default (IStatement type)
         */

        expr = e;
        expr1 = e1;
        expr2 = e2;
        stmt1 = s1;
        stmt2 = s2;
        stmt3 = s3;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Simulates Switch with two cases and one default statement
            Chooses one statement to be pushed on ExecutionStack from three given statements,
            depending on given cases
            If 'expr' or 'expr1' or 'expr2' is not IntType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IStack<IStatement> stack = state.getStack();
        IDictionary<String, Value> symTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        Value condition = expr.eval(symTable,heap);
        if(condition.getType().equals(new IntType())){

            Value e1 = expr1.eval(symTable,heap);
            if(e1.getType().equals(new IntType())) {

                Value e2 = expr2.eval(symTable,heap);
                if(e2.getType().equals(new IntType())) {

                    IStatement new_stmt = new IfStatement(new RelationalExpression(expr,expr1,"=="),stmt1,
                            new IfStatement(new RelationalExpression(expr,expr2,"=="),stmt2,stmt3));
                    stack.push(new_stmt);

                }
                else
                    throw new NotIntegerException(expr2.toString()+" is not int type");
            }
            else
                throw new NotIntegerException(expr1.toString()+" is not int type");
        }
        else
            throw new NotIntegerException(expr.toString()+" is not int type");

        return null;
    };

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws Exception{
        /*
            Checks if 'expr', 'expr1', 'expr2' are IntType
            Checks the correctness of the three statements on a deep copy of the given TypeEnvironment
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type expr_type = expr.typecheck(typeEnv);
        Type expr1_type = expr1.typecheck(typeEnv);
        Type expr2_type = expr2.typecheck(typeEnv);
        MyDictionary<String, Type> copyEnv1 = (MyDictionary<String, Type>) typeEnv.deepCopy();
        MyDictionary<String, Type> copyEnv2 = (MyDictionary<String, Type>) typeEnv.deepCopy();
        MyDictionary<String, Type> copyEnv3 = (MyDictionary<String, Type>) typeEnv.deepCopy();
        if(expr_type.equals(new IntType())){
            if(expr1_type.equals(new IntType())){
                if(expr2_type.equals(new IntType())) {
                    stmt1.typecheck(copyEnv1);
                    stmt2.typecheck(copyEnv2);
                    stmt3.typecheck(copyEnv3);
                    return typeEnv;
                }
                else
                    throw new NotIntegerException(expr2.toString()+" must be integer");
            }
            else
                throw new NotIntegerException(expr1.toString()+" must be integer");
        }
        else
            throw new NotIntegerException(expr.toString()+" must be integer");

    };

    @Override
    public IStatement deepCopy(){
        return new SwitchStatement(expr.deepCopy(),expr1.deepCopy(),expr2.deepCopy(),
                stmt1.deepCopy(),stmt2.deepCopy(),stmt3.deepCopy());
    };

    @Override
    public String toString(){
        return "switch("+expr.toString()+")(case("+expr1.toString()+"): "+stmt1.toString()
                +")(case("+expr2.toString()+"): "+stmt2.toString()+")(default: "+stmt3.toString()+")";
    }
}
