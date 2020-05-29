package Model;

/**
 * Provides information regarding inputs from the user. You may get the errorMessage and display it to the customer.
 */
public class RecordException extends Exception{

    String errorMessage;

public RecordException(String errorMessage){
    this.errorMessage=errorMessage;
}

    public String getErrorMessage() {
        return errorMessage;
    }
}
