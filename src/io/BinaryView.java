package io;

import java.io.*;
import java.text.ParseException;
import java.util.Arrays;

/**
 * Struct of byte representation:
 * int fieldLong; Field long in bytes
 * byte[] field; Bytes of data
 * and go on...
 */
public interface BinaryView extends Pathable
{
    byte AMOUNT_OF_BYTES_TO_CHAR_TYPE = 2;
    byte AMOUNT_OF_BYTES_TO_INT_TYPE = 4;
    int MAX_BUFFER_SIZE = 1024;
    /**
     * @return Binary representation of object.
     * */
    byte[] toBinary(Source source) throws IOException;
    void fromBinary(byte[] rawBytes, FileSource source) throws IOException, ParseException;
    /**
     *
     * @return Amount of bytes needed to represent the object
     */
    int getBytesAmount();

    /**
     *
     * @param str String to know amount of bytes
     * @return Amount of bytes to representation of the string
     */
    static int getBinarySizeOfString(String str)
    {
        return str.length()*AMOUNT_OF_BYTES_TO_CHAR_TYPE;
    }

    /**
     *
     * @param amountIntFields Amount of int fields in a row
     * @return Bytes amount to "allocate"
     */
    static int getBinarySizeOfInts(int amountIntFields)
    {
        return AMOUNT_OF_BYTES_TO_INT_TYPE*amountIntFields;
    }

    /**
     *
      * @param filename Name of file to write in
     * @param bytes Array of bytes to write in the file
     * @param create Create the file or just append in
     * @throws IOException Some IO exceptions, like write error and other...
     */
    static void writeBinaryFile(String filename, byte[] bytes, boolean create) throws IOException
    {
        if(create)
        {
            File f = new File(filename);
            f.createNewFile();
        }
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));
        dos.write(bytes);
        dos.flush();
        dos.close();
    }

    /**
     *
     * @param filename Name of file to read
     * @return Array of bytes read from file
     * @throws IOException IO exceptions
     */
    static byte[] readBinaryFile(String filename) throws IOException
    {
        DataInputStream dis = new DataInputStream(new FileInputStream(filename));
        byte[] readedBytes = new byte[dis.available()];
        int index = 0;
        while(dis.available() > 0)
        {
            readedBytes[index] = dis.readByte();
            index++;
        }
        dis.close();
        return readedBytes;
    }

    /**
     *
     * @param first First array to concat
     * @param second Second array to concat
     * @return Concated array by first and second
     */
    static byte[] appendToByteArray(byte[] first, byte[] second)
    {
        byte[] unitedArray = new byte[first.length + second.length];
        System.arraycopy(first, 0, unitedArray, 0, first.length);
        unitedArray = appendIntToByteArray(unitedArray, first.length, second.length);
        System.arraycopy(second, 0, unitedArray, first.length+AMOUNT_OF_BYTES_TO_INT_TYPE, second.length);
        return unitedArray;
    }

    /**
     *
     * @param array Array to add new element
     * @param b New element to add
     * @return Array with added element
     */
    static byte[] appendByteToByteArray(byte[] array, byte b)
    {
        byte[] newArray = new byte[array.length+1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray = appendIntToByteArray(newArray, 1, array.length);
        newArray[array.length+1] = b;
        return newArray;
    }

    /**
     *
     * @param array Source array to add integer
     * @param i Integer to add
     * @return Array with added integer at the end, without prefix(sizeof)
     */
    static byte[] appendIntToByteArray(byte[] array, int i)
    {
        byte[] newArray = new byte[array.length+AMOUNT_OF_BYTES_TO_INT_TYPE];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = (byte)((i & 0xFF000000) >> 24);//hi part
        newArray[array.length+1] = (byte)((i&0x00FF0000) >> 16);//hi2 part
        newArray[array.length+2] = (byte)((i&0x0000FF00) >> 8);//low2 part
        newArray[array.length+3] = (byte)(i&0x000000FF);//low part
        return newArray;
    }

    static byte[] appendIntToByteArray(byte[] array, int offset, int i)
    {
        byte[] newArray = new byte[array.length+AMOUNT_OF_BYTES_TO_INT_TYPE];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[offset++] = (byte)((i & 0xFF000000) >> 24);//hi part
        newArray[offset++] = (byte)((i&0x00FF0000) >> 16);//hi2 part
        newArray[offset++] = (byte)((i&0x0000FF00) >> 8);//low2 part
        newArray[offset] = (byte)(i&0x000000FF);//low part
        return newArray;
    }

    /**
     *
     * @param array Source array to add
     * @param i Integer to add
     * @return New array with prefix(sizeof of int) and data(integer)
     */
    static byte[] appendIntToByteArrayWithPrefix(byte[] array, int i)
    {
        return appendIntToByteArray(appendIntToByteArray(array, array.length, AMOUNT_OF_BYTES_TO_INT_TYPE), i);
    }

    /**
     *
     * @param array Source array to add boolean
     * @param b Boolean to add
     * @return Array with boolean at the end
     */
    static byte[] appendBoolToByteArray(byte[] array, boolean b)
    {
        return appendIntToByteArray(appendIntToByteArray(array, array.length, AMOUNT_OF_BYTES_TO_INT_TYPE), b ? 1 : 0);
    }

    /**
     * General comparing of bytes array.
     * Comparision taking the least length of arrays and compares values between arrays by indices
     * Which growing by the least length of arrays.
     * @param first First array to compare
     * @param firstOffset in first array to comparision
     * @param firstLength Amount of bytes to compare
     * @param second Second array to compare
     * @param secondOffset Offset in second array to comparision
     * @param secondLength Amount of bytes to compare
     * @return True if they are equal or false if they are not
     */
    static boolean compareBytesArray(byte[] first, int firstOffset, int firstLength, byte[] second, int secondOffset, int secondLength)
    {
        for(int i = 0;
            i < (firstLength < secondLength ? firstLength : secondLength); i++, firstOffset++, secondOffset++)
        {
            if(first[firstOffset] != second[secondOffset])
                return false;
        }
        return true;
    }

    /**
     * A less functional method, which compares arrays by taking length of first array as maximum bytes amount to compare.
     * @param first First array
     * @param second Second array
     * @return True if they're equal or false if they're not
     */
    static boolean compareBytesArray(byte[] first, byte[] second)
    {
        return compareBytesArray(first, 0, first.length, second, 0, first.length);
    }

    static String readStr(byte[] bytes, int number) throws IOException
    {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        int sizeBuffer;
        byte[] buffer;
        for(int i = 1; i < number; i++)
        {
            sizeBuffer = dis.readInt();
            if(sizeBuffer > MAX_BUFFER_SIZE) return null;
            dis.skipBytes(sizeBuffer);
        }
        sizeBuffer = dis.readInt();
        if(sizeBuffer > MAX_BUFFER_SIZE) return null;
        buffer = new byte[sizeBuffer];
        dis.read(buffer, 0, sizeBuffer);
        return new String(buffer);
    }

    static String readStrEx(byte[] bytes, int size) throws IOException
    {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        byte[] buffer = new byte[size];
        dis.read(buffer, 0, size);
        return new String(buffer);
    }

    static int readInt(byte[] bytes, int startOffset, int integerByteLength)
    {
        int readedInt = 0;
        for(int i = 0; i < integerByteLength; i++, startOffset++)
            readedInt |= bytes[startOffset+i];
        return readedInt;
    }
}
