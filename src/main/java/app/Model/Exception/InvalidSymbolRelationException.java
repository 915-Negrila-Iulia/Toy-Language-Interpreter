package app.Model.Exception;

public class InvalidSymbolRelationException extends Exception{

    public InvalidSymbolRelationException(){

    }

    public InvalidSymbolRelationException(String message){
        super(message);
    }

    public InvalidSymbolRelationException(String message, Throwable cause){
        super(message, cause);
    }
}
