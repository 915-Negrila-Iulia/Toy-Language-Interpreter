package app.Model.Exception;

public class NotBoolException extends Exception{

    public NotBoolException(){

    }

    public NotBoolException(String message){
        super(message);
    }

    public NotBoolException(String message, Throwable cause){

        super(message, cause);
    }
}
