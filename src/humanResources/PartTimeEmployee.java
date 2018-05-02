package humanResources;

import io.FileSource;
import io.Source;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.StringTokenizer;

import static io.BinaryView.*;


public class PartTimeEmployee extends Employee
{
    @Override
    public int getBonus() {
        return 0;
    }

    @Override
    public void setBonus(int bonus)
    {
    }

    public PartTimeEmployee(String firstName, String lastName, JobTitlesEnum jobTitle, Integer salary)
    {
        super(firstName, lastName, jobTitle, salary);
    }

    public PartTimeEmployee(String firstName, String lastName)
    {
        super(firstName, lastName, JobTitlesEnum.NONE,  DEFAULT_SALARY);
    }

    public PartTimeEmployee(String rawData)
    {
        this("", "");
        StringTokenizer st = new StringTokenizer(rawData, defaultFieldsDelimiter);
        st.nextToken();
        this.firstName = st.nextToken();
        this.lastName = st.nextToken();
        this.jobTitle = JobTitlesEnum.values()[Integer.parseInt(st.nextToken())];
        this.salary = Integer.parseInt(st.nextToken());
    }

    @Override
    public String toString()
    {
        return String.format("%s %s, %s(Внешний совместитель), %dр.", lastName, firstName, jobTitle, salary);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof PartTimeEmployee)
        {
            Employee employee = (Employee) obj;
            return super.equals(employee);
        }
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }


    @Override
    public String toText()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(defaultFieldsDelimiter).
                append(super.toText());
        return sb.toString();
    }

    @Override
    public String toText(Source source) {
        return toText();
    }

    @Override
    public PartTimeEmployee fromText(String text)
    {
        return new PartTimeEmployee(text);
    }

    @Override
    public Employee fromText(String text, FileSource source) {
        return null;
    }


    @Override
    public void fromBinary(byte[] rawBytes, FileSource source) throws IOException, ParseException
    {
        super.fromBinary(rawBytes, null);
    }

    @Override
    public int getBytesAmount() {
        return 0;
    }
}
