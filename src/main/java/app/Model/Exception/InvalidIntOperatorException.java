package app.Model.Exception;

public class InvalidIntOperatorException extends Exception{

    public InvalidIntOperatorException(){

    }

    public InvalidIntOperatorException(String message){
        super(message);
    }

    public InvalidIntOperatorException(String message, Throwable cause){
        super(message, cause);
    }
}
