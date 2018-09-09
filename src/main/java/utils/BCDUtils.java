package utils;

public class BCDUtils {
    public static byte[] decimalToBCD(long num) {
        int digits = 0;

        long temp = num;
        while (temp != 0) {
            digits++;
            temp /= 10;
        }

        int byteLen = digits % 2 == 0 ? digits / 2 : (digits + 1) / 2;

        byte bcd[] = new byte[byteLen];

        for (int i = 0; i < digits; i++) {
            byte tmp = (byte) (num % 10);

            if (i % 2 == 0) {
                bcd[i / 2] = tmp;
            } else {
                bcd[i / 2] |= (byte) (tmp << 4);
            }

            num /= 10;
        }

        for (int i = 0; i < byteLen / 2; i++) {
            byte tmp = bcd[i];
            bcd[i] = bcd[byteLen - i - 1];
            bcd[byteLen - i - 1] = tmp;
        }

        return bcd;
    }

    public static byte[] decimalToBCD(long num, int fixedSize) {
        byte[] original = decimalToBCD(num);

        if (original.length != fixedSize) {
            byte[] filled = new byte[fixedSize];
            for (int i = fixedSize - 1; i > original.length - 1 - 1; i--) {
                System.out.println(original.length);
                filled[i] = original[i - (fixedSize - original.length)];
            }
            return filled;
        }

        return original;
    }

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
