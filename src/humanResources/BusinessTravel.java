package humanResources;

import java.util.Calendar;
import java.util.Date;

public final class BusinessTravel
{
    private final String destination;
    private final String description;
    private final int compensation;
    private final Calendar beginDate;
    private final Calendar endDate;
    private long daysCount = -1;

    public BusinessTravel(String destination, int compensation, String description, Calendar beginDate, Calendar endDate)
    {
        if(compensation < 0 || endDate.getTime().getTime() < beginDate.getTime().getTime()) throw new IllegalArgumentException();
        this.description = description;
        this.destination = destination;
        this.compensation = compensation;
        this.beginDate = beginDate;
        this.endDate = endDate;
        getDaysCount();
    }

    public BusinessTravel()
    {
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.set(Calendar.HOUR, 0);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        begin.set(Calendar.MILLISECOND, 0);
        end.set(Calendar.HOUR, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        this.description = "";
        this.destination = "";
        this.compensation = 0;
        this.beginDate = begin;
        end.add(Calendar.DAY_OF_YEAR, 1);
        this.endDate = end;
        getDaysCount();
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

    private long getDaysCount()
    {
        if(daysCount == -1) daysCount = (long)Math.ceil((((double)(endDate.getTime().getTime() - beginDate.getTime().getTime()))/(double)86400000));//1000(seconds)/3600(hours)/24(days)
        return daysCount;
    }

    @Override
    public String toString()
    {
        getDaysCount();
        //StringBuilder sb = new StringBuilder();
        String line = "";
        if(this.destination != null && !this.destination.isEmpty())
            line = String.format("%s", this.destination);//sb.append(this.destination).append(" ");
        if(this.daysCount != 0)
            line = String.format("%s %d", line, daysCount);//sb.append(this.daysCount).append(" ");
        if(this.compensation != 0)
            line = String.format("%s (%d)", line, compensation);//sb.append("(").append(this.compensation).append("). ");
        if(this.description != null && !this.description.isEmpty())
            line = String.format("%s %s", line, description);//sb.append(this.description);
        return line;//String.format("%s", sb.toString());
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof  BusinessTravel)
                && (this.description.equals(((BusinessTravel)obj).description))
                && (this.compensation == ((BusinessTravel)obj).compensation)
                && (this.daysCount == ((BusinessTravel)obj).daysCount)
                && (this.beginDate.equals(((BusinessTravel) obj).beginDate))
                && (this.endDate.equals(((BusinessTravel) obj).endDate))
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

    public Calendar getBeginDate()
    {
        return beginDate;
    }

    public Calendar getEndDate()
    {
        return endDate;
    }

}
