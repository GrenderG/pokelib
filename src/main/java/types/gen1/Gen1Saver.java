package types.gen1;

import utils.ByteUtils;

import java.util.Arrays;
import java.util.List;

public class Gen1Saver {

    public byte[] encode(String string) {
        final StringBuilder encoded = new StringBuilder();
        final List<String> tempList = Arrays.asList(Data.encodingTable);
        for (char c : string.toCharArray()) {
            encoded.append(String.valueOf(Integer.valueOf(String.valueOf(tempList.indexOf(String.valueOf(c))), 16)));
        }
        return ByteUtils.hexStringToByteArray(encoded.toString());
    }
}
