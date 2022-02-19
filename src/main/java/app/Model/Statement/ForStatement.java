package app.Model.Statement;

import app.Model.ADT.IDictionary;
import app.Model.ADT.IHeap;
import app.Model.ADT.IStack;
import app.Model.ADT.MyDictionary;
import app.Model.Exception.NotIntegerException;
import app.Model.Exception.TypesDontMatchException;
import app.Model.Exception.VariableNotDefinedException;
import app.Model.ProgramState;
import app.Model.ToyExpression.Expression;
import app.Model.ToyExpression.RelationalExpression;
import app.Model.ToyExpression.VariableExpression;
import app.Model.ToyType.IntType;
import app.Model.ToyType.Type;
import app.Model.ToyValue.IntValue;
import app.Model.ToyValue.Value;

public class ForStatement implements IStatement{

    /*
        ForStatement class implements abstract methods of IStatement interface
     */

    private final String id;
    private final Expression expr1;
    private final Expression expr2;
    private final Expression expr3;
    private final IStatement statement;

    public ForStatement(String var, Expression e1, Expression e2, Expression e3, IStatement s){
        /*
             Parametrised constructor which creates a ForStatement object
            :param var: name of a variable (String type)
            :param e1: expression having the initial value of 'var' variable (Expression type)
            :param e2: condition (Expression type)
            :param e3: expression having the new value of 'var' variable (Expression type)
            :param s: statement to be executed (IStatement type)
         */

        this.id = var;
        this.expr1 = e1;
        this.expr2 = e2;
        this.expr3 = e3;
        this.statement = s;
    }

    @Override
    public ProgramState execute (ProgramState state) throws Exception{
        /*
            Simulates For loop
            If the variable ('id') is not defined in SymbolTable a custom Expression is thrown
            If 'expr1' or 'expr3' are not IntType or 'expr2' is not BoolType a custom Expression is thrown
            :param state: state of the program (ProgramState type)
            :return: null
         */

        IStack<IStatement> stack = state.getStack();
        IDictionary<String, Value> symTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        try{
            if(symTable.isDefined(id)){
                Value value = expr1.eval(symTable,heap);
                if (((Value) value).getType().equals(new IntType())) {
                    IntValue init_value = (IntValue) value;

                    Value condition = expr2.eval(symTable,heap);
                    if(condition.getType().equals(new IntType())){
                        IntValue cond_value = (IntValue) condition;

                        Value value3 = expr3.eval(symTable,heap);
                        if (((Value) value3).getType().equals(new IntType())) {
                            IntValue update_value = (IntValue) value3;

                            IStatement new_stmt = new CompoundStatement(new AssignmentStatement("v",expr1),
                                    new WhileStatement(new RelationalExpression(new VariableExpression("v"),
                                            expr2,"<"),new CompoundStatement(statement,
                                            new AssignmentStatement("v",expr3))));
                            stack.push(new_stmt);

                        }
                        else
                            throw new TypesDontMatchException("type "+expr3+" is not int");

                    }
                    else
                        throw new NotIntegerException(expr2.toString()+" is not bool type");


                }else{
                    throw new TypesDontMatchException("type "+expr1+" is not int");
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
            Checks if types of 'expr1' and 'expr3' are IntType and if type of 'expr2' is BoolType
            Checks the correctness of the statement 'stmt' on a deep copy of the given TypeEnvironment
            Throw exception if checking does not pass
            :param typeEnv: reference to the TypeEnvironment dictionary
            :return: TypeEnvironment dictionary
         */

        Type type_variable = typeEnv.lookUp(id);
        Type type_expr1 = expr1.typecheck(typeEnv);
        Type type_expr2 = expr2.typecheck(typeEnv);
        Type type_expr3 = expr3.typecheck(typeEnv);
        MyDictionary<String,Type> copyEnv = (MyDictionary<String, Type>) typeEnv.deepCopy();
        if(type_variable.equals(type_expr1) && type_expr1.equals(new IntType())) {
            if (type_expr2.equals(new IntType())) {
                if (type_expr3.equals(new IntType())) {
                    statement.typecheck(copyEnv);
                    return typeEnv;
                } else
                    throw new NotIntegerException("For: "+expr1+" not int type ");
            }else
                throw new NotIntegerException("For : "+expr2+" not int type");
        }else
            throw new NotIntegerException("For : "+expr3+" not int type");
    }

    @Override
    public IStatement deepCopy(){
        return new ForStatement(id,expr1.deepCopy(),expr2.deepCopy(),expr3.deepCopy(),statement.deepCopy());
    }

    @Override
    public String toString(){
        return "for("+id+"="+expr1.toString()+"; " +id+"<"+expr2.toString()+"; "
                +id+"= "+expr3.toString()+") { "+statement.toString()+" }";
    }
}
