package utils;

public class ByteUtils {
    public static byte[] hexStringToByteArray(String string) {
        int len = string.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
                    + Character.digit(string.charAt(i + 1), 16));
        }
        return data;
    }

    // Converts unsigned byte array to int
    public static int bytesToInt(byte[] bytes) {
        int res = 0;
        for (byte b : bytes) {
            res = (res << 8) + (b & 0xFF);
        }
        return res;
    }

    // Converts unsigned byte to int
    public static int byteToInt(byte b) {
        return b & 0xFF;
    }
}
