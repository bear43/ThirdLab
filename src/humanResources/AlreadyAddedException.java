package humanResources;

class AlreadyAddedException extends Exception
{
    AlreadyAddedException()
    {
        super("Current element has been added already");
    }
}
