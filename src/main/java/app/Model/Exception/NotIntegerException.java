package app.Model.Exception;

public class NotIntegerException extends Exception{

    public NotIntegerException(){

    }

    public NotIntegerException(String message){
        super(message);
    }

    public NotIntegerException(String message, Throwable cause){
        super(message, cause);
    }
}
