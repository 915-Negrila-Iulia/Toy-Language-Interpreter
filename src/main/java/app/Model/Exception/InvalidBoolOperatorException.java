package app.Model.Exception;

public class InvalidBoolOperatorException extends Exception{

    public InvalidBoolOperatorException(){

    }

    public InvalidBoolOperatorException(String message){
        super(message);
    }

    public InvalidBoolOperatorException(String message, Throwable cause){

        super(message, cause);
    }
}
