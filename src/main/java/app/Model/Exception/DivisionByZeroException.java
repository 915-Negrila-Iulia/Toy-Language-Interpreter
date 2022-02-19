package app.Model.Exception;

public class DivisionByZeroException extends Exception{

    public DivisionByZeroException(){

    }

    public DivisionByZeroException(String message){
        super(message);
    }

    public DivisionByZeroException(String message, Throwable cause){

        super(message, cause);
    }
}
