package utils;

public class ByteUtils {
    public static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    public static int bytesToInt(byte[] bytes) {
        int res = 0;
        for (byte b : bytes) {
            res = (res << 8) + (b & 0xFF);
        }
        return res;
    }

    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    public static byte[] intToBytes(int value, int size) {
        byte[] res = new byte[size];
        for (int i = 0; i < size; i++) {
            res[i] = (byte) (value >>> (8 * (size - 1 - i)));
        }
        return res;
    }

    public static byte intToByte(int value) {
        return (byte) (value & 0xFF);
    }

    public static byte[] genNullByteArray(int size) {
        byte[] empty = new byte[size];
        for (int i = 0; i < size; i++) {
            empty[i] = 0x50;
        }
        return empty;
    }

    public static byte[] reverseByteArray(byte[] arrayToReverser) {
        byte[] reversed = new byte[arrayToReverser.length];
        for (int i = 0; i < arrayToReverser.length; i++) {
            reversed[i] = arrayToReverser[arrayToReverser.length - 1 - i];
        }
        return reversed;
    }
}
