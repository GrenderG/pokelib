package utils;

public class BCDUtils {
    public static long BCDToDecimal(byte[] bcd) {
        return Long.valueOf(BCDtoString(bcd));
    }

    public static String BCDtoString(byte bcd) {
        StringBuilder sb = new StringBuilder();

        byte high = (byte) (bcd & 0xF0);
        high >>>= (byte) 4;
        high = (byte) (high & 0x0F);
        byte low = (byte) (bcd & 0x0F);

        sb.append(high);
        sb.append(low);

        return sb.toString();
    }

    public static String BCDtoString(byte[] bcd) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bcd) {
            sb.append(BCDtoString(b));
        }

        return sb.toString();
    }
}
