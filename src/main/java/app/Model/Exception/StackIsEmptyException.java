package app.Model.Exception;

public class StackIsEmptyException extends Exception{

    public StackIsEmptyException(){

    }

    public StackIsEmptyException(String message){

        super(message);
    }

    public StackIsEmptyException(String message, Throwable cause){

        super(message, cause);
    }

}
