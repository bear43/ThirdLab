package io;

import java.io.IOException;

/**
 * Struct of byte representation:
 * int fieldLong; Field long in bytes
 * byte[] field; Bytes of data
 * and go on...
 */
public interface BinaryView extends Pathable
{
    static final byte AMOUNT_OF_BYTES_TO_CHAR_TYPE = 2;
    static final byte AMOUNT_OF_BYTES_TO_INT_TYPE = 4;
    /**
     * @return Binary representation of object.
     * */
    byte[] toBinary(Source source) throws IOException;
    void fromBinary(byte[] rawBytes, Source source);
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
}
