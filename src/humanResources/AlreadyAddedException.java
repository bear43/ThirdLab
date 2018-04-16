package humanResources;

class AlreadyAddedException extends Exception
{
    //todo DONE
    AlreadyAddedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    AlreadyAddedException(String message)
    {
        super(message);
    }

    AlreadyAddedException()
    {
        super("Current element has been added already");
    }
}