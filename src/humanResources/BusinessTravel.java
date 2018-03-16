package humanResources;

public final class BusinessTravel
{
    private final String destination;
    private final String description;
    private final int compensation;
    private final int daysCount;

    public BusinessTravel(String destination, int daysCount, int compensation, String description)
    {
        this.description = description;
        this.destination = destination;
        this.compensation = compensation;
        this.daysCount = daysCount;
    }

    public BusinessTravel()
    {
        this("", 0, 0, "");
    }

    public String getDestination()
    {
        return destination;
    }

    public String getDescription()
    {
        return description;
    }

    public int getCompensation()
    {
        return compensation;
    }

    public int getDaysCount()
    {
        return daysCount;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if(this.destination != null && !this.destination.isEmpty())
            sb.append(this.destination).append(" ");
        if(this.daysCount != 0)
            sb.append(this.daysCount).append(" ");
        if(this.compensation != 0)
            sb.append("(").append(this.compensation).append("). ");
        if(this.description != null && !this.description.isEmpty())
            sb.append(this.description);
        return sb.toString();
    }

    //todo CHECK
    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof  BusinessTravel)
                && (this.description.equals(((BusinessTravel)obj).description))
                && (this.compensation == ((BusinessTravel)obj).compensation)
                && (this.daysCount == ((BusinessTravel)obj).daysCount)
                && (this.destination.equals(((BusinessTravel)obj).destination));
    }

    @Override
    public int hashCode()
    {
        int res = this.description.hashCode();
        res ^= this.compensation;
        res ^= this.daysCount;
        res ^= this.destination.hashCode();
        return res;
    }
}
