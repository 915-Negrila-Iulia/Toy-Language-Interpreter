package app.Model.Exception;

public class AlreadyDeclaredException extends Exception{

    public AlreadyDeclaredException(){

    }

    public AlreadyDeclaredException(String message){
        super(message);
    }

    public AlreadyDeclaredException(String message, Throwable cause){

        super(message, cause);
    }

}
