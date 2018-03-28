package humanResources;

public class IllegalDatesException extends Exception
{
    //todo three empty constructor, message, throwable DONE
    IllegalDatesException(String message, Throwable cause)
    {
        super(message, cause);
    }

    IllegalDatesException(String message)
    {
        super(message);
    }

    IllegalDatesException()
    {
        super("Illegal date. End equals begin");
    }
}
