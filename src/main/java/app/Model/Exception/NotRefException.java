package app.Model.Exception;


public class NotRefException extends Exception{

    public NotRefException(){

    }

    public NotRefException(String message){
        super(message);
    }

    public NotRefException(String message, Throwable cause){
        super(message, cause);
    }
}
