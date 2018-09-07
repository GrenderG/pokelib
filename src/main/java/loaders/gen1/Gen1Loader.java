package loaders.gen1;

import models.*;
import utils.BCDUtils;
import utils.ByteUtils;
import utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Gen1Loader {
    private static final String[] encodingTable = {
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "(", ")",
            ":", ";", "[", "]", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "'", "", "", "-", "", "", "?", "!", ".", "", "", "", "", "", "", "",
            "", "", ".", "/", ",", "", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private byte[] saveInMemory;

    public boolean loadSaveInMemory(File saveFile) {
        try {
            saveInMemory = Files.readAllBytes(saveFile.toPath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String decode(byte[] hexList) {
        final StringBuilder decoded = new StringBuilder();
        for (byte b : hexList) {
            decoded.append(encodingTable[Integer.parseInt(String.format("%02X", b), 16)]);
        }
        return decoded.toString();
    }

    public byte[] encode(String string) {
        final StringBuilder encoded = new StringBuilder();
        final List<String> tempList = Arrays.asList(encodingTable);
        for (char c : string.toCharArray()) {
            encoded.append(String.valueOf(Integer.valueOf(String.valueOf(tempList.indexOf(String.valueOf(c))), 16)));
        }
        return ByteUtils.hexStringToByteArray(encoded.toString());
    }

    private int fixIndex(int indexToFix) {
        if (indexToFix < 0)
            return indexToFix + 256;
        return indexToFix;
    }

    /*private long decodePlayerId() {
        long id = 0;
        byte[] encodedId = Arrays.copyOfRange(saveInMemory, 0x2605, 0x2605 + 0x2);
        for (byte b : encodedId) {
            id = (id << 8) + (b & 0xFF);
        }
        return id;
    }*/

    private Item[] generateItemContainer(int hexStartAddress, int size) {
        Item[] items = new Item[size];

        for (int i = 0; i < items.length; i++) {
            int itemIndex = fixIndex(saveInMemory[hexStartAddress + 0x1 + 0x2 * i]);
            items[i] = new Item(
                    itemIndex, Data.items[itemIndex],
                    fixIndex(saveInMemory[hexStartAddress + 0x2 + 0x2 * i])
            );
        }

        return items;
    }

    private byte[] loadPokemonBytes(int offHex, int offOtName, int offName, int offData, boolean isBox) {
        byte[] pokemonBytes = new byte[1 + 11 + 11 + (isBox ? 33 : 44)];
        pokemonBytes[0] = saveInMemory[offHex];
        System.arraycopy(saveInMemory, offOtName - 1, pokemonBytes, 1, 11);
        System.arraycopy(saveInMemory, offName - 1, pokemonBytes, 11, 11);
        System.arraycopy(saveInMemory, offData - 1, pokemonBytes, 22, isBox ? 33 : 44);
        return pokemonBytes;
    }

    private Pokemon decodePokemon(byte[] pokemonBytes, boolean isBox) {
        int specieIndex = fixIndex(pokemonBytes[0]);
        int type1Index = fixIndex(pokemonBytes[28]);
        int type2Index = fixIndex(pokemonBytes[29]);
        int move1Index = fixIndex(pokemonBytes[31]);
        int move2Index = fixIndex(pokemonBytes[32]);
        int move3Index = fixIndex(pokemonBytes[33]);
        int move4Index = fixIndex(pokemonBytes[34]);

        return new Pokemon(
                decode(Arrays.copyOfRange(pokemonBytes, 12, 23)),
                new Specie(specieIndex, Data.pokemons[specieIndex]),
                fixIndex(pokemonBytes[25]) + fixIndex(pokemonBytes[24]) * 256,
                fixIndex(pokemonBytes[26]),
                fixIndex(pokemonBytes[27]) == 0x04,
                fixIndex(pokemonBytes[27]) == 0x07,
                fixIndex(pokemonBytes[27]) == 0x10,
                fixIndex(pokemonBytes[27]) == 0x20,
                fixIndex(pokemonBytes[27]) == 0x40,
                new Type(type1Index, Data.types[type1Index]),
                new Type(type2Index, Data.types[type2Index]),
                fixIndex(pokemonBytes[30]),
                new Move(move1Index, Data.moves[move1Index], pokemonBytes[52]),
                new Move(move2Index, Data.moves[move2Index], pokemonBytes[53]),
                new Move(move3Index, Data.moves[move3Index], pokemonBytes[54]),
                new Move(move4Index, Data.moves[move4Index], pokemonBytes[55]),
                fixIndex(pokemonBytes[36]) + fixIndex(pokemonBytes[35]) * 256,
                decode(Arrays.copyOfRange(pokemonBytes, 1, 11)),
                fixIndex(pokemonBytes[39]) + fixIndex(pokemonBytes[38]) * 256 + fixIndex(pokemonBytes[37]) * 65536,
                fixIndex(pokemonBytes[41]) + fixIndex(pokemonBytes[40]) * 256,
                fixIndex(pokemonBytes[43]) + fixIndex(pokemonBytes[42]) * 256,
                fixIndex(pokemonBytes[45]) + fixIndex(pokemonBytes[44]) * 256,
                fixIndex(pokemonBytes[47]) + fixIndex(pokemonBytes[46]) * 256,
                fixIndex(pokemonBytes[49]) + fixIndex(pokemonBytes[48]) * 256,
                -1,
                isBox ? -1 : fixIndex(pokemonBytes[50]) >> 4,
                isBox ? -1 : fixIndex(pokemonBytes[50]) & 15,
                isBox ? -1 : fixIndex(pokemonBytes[51]) >> 4,
                isBox ? -1 : fixIndex(pokemonBytes[51]) & 15,
                isBox ? -1 : fixIndex(pokemonBytes[56]),
                isBox ? -1 : fixIndex(pokemonBytes[58]) + fixIndex(pokemonBytes[57]) * 256,
                isBox ? -1 : fixIndex(pokemonBytes[60]) + fixIndex(pokemonBytes[59]) * 256,
                isBox ? -1 : fixIndex(pokemonBytes[62]) + fixIndex(pokemonBytes[61]) * 256,
                isBox ? -1 : fixIndex(pokemonBytes[64]) + fixIndex(pokemonBytes[63]) * 256,
                isBox ? -1 : fixIndex(pokemonBytes[66]) + fixIndex(pokemonBytes[65]) * 256
        );
    }

    private Pokemon[] generatePartyPokemons() {
        Pokemon[] pokemons = new Pokemon[6];

        for (int i = 0; i < pokemons.length; i++) {
            byte[] pokemon = loadPokemonBytes(0x2F2C + 0x1 + i, 0x303C + 0xB * i, 0x307E + 0xB * i, 0x2F34 + 0x2C * i, false);
            pokemons[i] = decodePokemon(pokemon, false);
        }

        return pokemons;
    }

    private Pokemon[] generatePcPokemons() {
        Pokemon[] pokemons = new Pokemon[240];
        int currentBox = (saveInMemory[0x284C] & 127) + 1;

        for (int i = 0; i < 12; i++) {
            int offset;
            if (i == currentBox)
                offset = 0x30C0;
            else
                offset = 0x4000 + i / 6 * 8192 + 1122 * (i % 6);
            for (int j = 0; j < 20; j++) {
                byte[] pokemon = loadPokemonBytes(offset + 0x1 + j, offset + 682 + 0xB * j, offset + 902 + 0xB * j, offset + 22 + 0x21 * j, true);
                pokemons[20 * i + j] = decodePokemon(pokemon, true);
            }
        }

        return pokemons;
    }

    public Player getPlayer() {
        return new Player(
                fixIndex(saveInMemory[0x2605 + 0x1]) + fixIndex(saveInMemory[0x2605]) * 256,
                decode(Arrays.copyOfRange(saveInMemory, 0x2598, 0x2598 + 0xB)),
                decode(Arrays.copyOfRange(saveInMemory, 0x25F6, 0x25F6 + 0xB)),
                BCDUtils.BCDToDecimal(Arrays.copyOfRange(saveInMemory, 0x25F3, 0x25F3 + 0x3)),
                (int) BCDUtils.BCDToDecimal(Arrays.copyOfRange(saveInMemory, 0x2850, 0x2850 + 0x2)),
                TimeUtils.playedToMilliseconds(
                        fixIndex(saveInMemory[0x2CED]) + fixIndex(saveInMemory[0x2CEE]) * 256,
                        fixIndex(saveInMemory[0x2CEF]),
                        fixIndex(saveInMemory[0x2CF0])
                ),
                generateItemContainer(0x25C9, 19),
                generateItemContainer(0x27E6 , 49),
                generatePartyPokemons(),
                generatePcPokemons());
    }
}
