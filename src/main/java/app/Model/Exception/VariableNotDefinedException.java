package app.Model.Exception;

public class VariableNotDefinedException extends Exception{

    public VariableNotDefinedException(){

    }

    public VariableNotDefinedException(String message){
        super(message);
    }

    // a caught exception can be re-thrown
    public VariableNotDefinedException(String message, Throwable cause){
        super(message,cause);
    }

}
