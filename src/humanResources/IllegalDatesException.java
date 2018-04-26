package humanResources;

public class IllegalDatesException extends Exception
{
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
