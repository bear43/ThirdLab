package humanResources;

public class IllegalDatesException extends Exception
{
    IllegalDatesException()
    {
        super("Illegal date. End equals begin");
    }
}
