package humanResources;

public final class BusinessTravel
{
    private final String destination;
    private final String description;
    private final Integer compensation;
    private final Integer daysCount;

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
        if(this.daysCount != null && !this.daysCount.equals(0))
            sb.append(this.daysCount).append(" ");
        if(this.compensation != null && !this.compensation.equals(0))
            sb.append("(").append(this.compensation).append("). ");
        if(this.description != null && !this.description.isEmpty())
            sb.append(this.description);
        return sb.toString();
    }

    //todo
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof BusinessTravel)
        {
            BusinessTravel bt = (BusinessTravel)obj;
            if(!this.description.equals(bt.description)) return false;
            if(!this.compensation.equals(bt.compensation)) return false;
            if(!this.daysCount.equals(bt.daysCount)) return false;
            if(!this.destination.equals(bt.destination)) return false;
        }
        else
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int res = this.description.hashCode();
        res ^= this.compensation.hashCode();
        res ^= this.daysCount.hashCode();
        res ^= this.destination.hashCode();
        return res;
    }
}
