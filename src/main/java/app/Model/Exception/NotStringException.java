package app.Model.Exception;

public class NotStringException extends Exception{

    public NotStringException(){

    }

    public NotStringException(String message){
        super(message);
    }

    public NotStringException(String message, Throwable cause){
        super(message, cause);
    }
}
