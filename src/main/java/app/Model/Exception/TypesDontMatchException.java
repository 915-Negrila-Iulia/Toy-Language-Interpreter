package app.Model.Exception;

public class TypesDontMatchException extends Exception{

    public TypesDontMatchException(){

    }

    public TypesDontMatchException(String message){
        super(message);
    }

    public TypesDontMatchException(String message, Throwable cause){
        super(message, cause);
    }

}
