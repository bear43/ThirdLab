package humanResources;

import io.BinaryView;
import io.FileSource;
import io.Source;
import io.Textable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import static io.BinaryView.*;

public final class BusinessTravel implements Textable<BusinessTravel>, BinaryView
{
    private static final String defaultPattern = "dd.MM.yyyy hh.mm";
    private static final String defaultExtension = "trl";
    private final String destination;
    private final String description;
    private final int compensation;
    private final Calendar beginDate;
    private final Calendar endDate;
    private long daysCount = -1;

    public BusinessTravel(String destination, int compensation, String description, Calendar beginDate, Calendar endDate)
    {
        if(compensation < 0) throw new IllegalArgumentException("Compensation cannot be negative");
        if(endDate.before(beginDate)) throw new IllegalArgumentException("Begin date cannot be before end date");
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
        this.daysCount = 1;
    }

    public BusinessTravel(String rawData) throws ParseException
    {
        StringTokenizer st = new StringTokenizer(rawData, defaultFieldsDelimiter);
        if(st.nextToken().equals(this.getClass().getName())) {
            this.destination = st.nextToken();
            this.description = st.nextToken();
            this.compensation = Integer.parseInt(st.nextToken());
            SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
            this.beginDate = Calendar.getInstance();
            this.beginDate.setTime(sdf.parse(st.nextToken()));
            this.endDate = Calendar.getInstance();
            this.endDate.setTime(sdf.parse(st.nextToken()));
            this.daysCount = Integer.parseInt(st.nextToken());
        }
        else
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
            this.daysCount = 1;
        }
    }

    public BusinessTravel(byte[] byteRepresentation) throws IOException, ParseException
    {
        int bufferSize;
        byte[] buffer = new byte[MAX_BUFFER_SIZE];
        SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(byteRepresentation));
        dis.skipBytes(dis.readInt());
        bufferSize = dis.readInt();
        dis.read(buffer, 0, bufferSize);
        this.destination = readStrEx(buffer, bufferSize);
        Arrays.fill(buffer,(byte)(0));
        bufferSize = dis.readInt();
        dis.read(buffer, 0, bufferSize);
        this.description = readStrEx(buffer, bufferSize);
        Arrays.fill(buffer,(byte)(0));
        this.compensation = dis.readInt();
        this.beginDate = Calendar.getInstance();
        this.endDate = Calendar.getInstance();
        bufferSize = dis.readInt();
        dis.read(buffer, 0, bufferSize);
        this.beginDate.setTime(sdf.parse(readStrEx(buffer, bufferSize)));
        Arrays.fill(buffer,(byte)(0));
        bufferSize = dis.readInt();
        dis.read(buffer, 0, bufferSize);
        this.endDate.setTime(sdf.parse(readStrEx(buffer, bufferSize)));
        Arrays.fill(buffer,(byte)(0));
        this.daysCount = dis.readInt();
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
        return String.format("%s, %d (%d), %s", this.destination, this.daysCount, this.compensation, this.description);
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

    @Override
    public String toText()
    {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
        sb.append(this.getClass().getName()).append(defaultFieldsDelimiter).
                append(this.destination.equals("") ? " " : this.destination).append(defaultFieldsDelimiter).
                append(this.description.equals("") ? " " : this.description).append(defaultFieldsDelimiter).
                append(this.compensation).append(defaultFieldsDelimiter).
                append(sdf.format(this.beginDate.getTime())).append(defaultFieldsDelimiter).
                append(sdf.format(this.endDate.getTime())).append(defaultFieldsDelimiter).
                append(this.daysCount);
        return sb.toString();
    }

    @Override
    public String toText(Source source){
        return toText();
    }

    @Override
    public BusinessTravel fromText(String text) throws  IOException, ParseException
    {
        return new BusinessTravel(text);
    }

    @Override
    public BusinessTravel fromText(String text, FileSource source){
        return null;
    }

    @Override
    public String getFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
        return String.format("TO_%s_%s_%s.%s", this.destination, sdf.format(this.beginDate.getTime()), sdf.format(this.endDate.getTime()), defaultExtension);
    }

    @Override
    public byte[] toBinary(Source source)
    {
        byte[] byteRepresentation = new byte[0];
        SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
        byteRepresentation = appendToByteArray(byteRepresentation, this.getClass().getName().getBytes());
        byteRepresentation = appendToByteArray(byteRepresentation, this.destination.getBytes());
        byteRepresentation = appendToByteArray(byteRepresentation, this.description.getBytes());
        byteRepresentation = appendIntToByteArray(byteRepresentation, this.compensation);
        byteRepresentation = appendToByteArray(byteRepresentation, sdf.format(this.beginDate.getTime()).getBytes());
        byteRepresentation = appendToByteArray(byteRepresentation, sdf.format(this.endDate.getTime()).getBytes());
        byteRepresentation = appendIntToByteArray(byteRepresentation, (int)(this.daysCount));
        return byteRepresentation;
    }

    @Override
    public void fromBinary(byte[] rawBytes, FileSource source) {

    }

    @Override
    public int getBytesAmount() {
        return 0;
    }
}
