package types.gen1;

import models.*;
import utils.BCDUtils;
import utils.ByteUtils;
import utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class Gen1Loader {
    private byte[] originalSaveInMemory;
    private byte[] saveInMemory;

    public boolean loadSaveInMemory(File saveFile) {
        try {
            saveInMemory = Files.readAllBytes(saveFile.toPath());
            originalSaveInMemory = new byte[saveInMemory.length];
            System.arraycopy(saveInMemory, 0, originalSaveInMemory, 0, saveInMemory.length);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public byte[] getSaveInMemory() {
        return saveInMemory;
    }

    public byte[] getOriginalSaveInMemory() {
        return originalSaveInMemory;
    }

    private String decode(byte[] hexList) {
        final StringBuilder decoded = new StringBuilder();
        for (byte b : hexList) {
            decoded.append(Data.encodingTable[Integer.parseInt(String.format("%02X", b), 16)]);
        }
        return decoded.toString();
    }

    private Item[] generateItemContainer(int hexStartAddress, int size) {
        Item[] items = new Item[size];

        for (int i = 0; i < items.length; i++) {
            int itemIndex = ByteUtils.byteToInt(saveInMemory[hexStartAddress + 0x1 + 0x2 * i]);
            if (itemIndex == 255)
                items[i] = null;
            items[i] = new Item(
                    itemIndex, Data.items[itemIndex],
                    ByteUtils.byteToInt(saveInMemory[hexStartAddress + 0x2 + 0x2 * i])
            );
        }

        return items;
    }

    private byte[] loadPokemonBytes(int offHex, int offOtName, int offName, int offData, boolean isBox) {
        byte[] pokemonBytes = new byte[1 + 11 + 11 + (isBox ? 33 : 44)];
        pokemonBytes[0] = saveInMemory[offHex];
        System.arraycopy(saveInMemory, offOtName - 1, pokemonBytes, 1, 10 + 1);
        System.arraycopy(saveInMemory, offName - 1, pokemonBytes, 11, 10 + 1);
        System.arraycopy(saveInMemory, offData - 1, pokemonBytes, 22, (isBox ? 33 : 44) + 1);
        return pokemonBytes;
    }

    public Pokemon decodePokemon(byte[] pokemonBytes, boolean isBox) {
        int specieIndex = ByteUtils.byteToInt(pokemonBytes[0]);
        if (specieIndex == 255)
            return null;
        int sprite = ByteUtils.byteToInt(pokemonBytes[23]);
        int type1Index = ByteUtils.byteToInt(pokemonBytes[28]);
        int type2Index = ByteUtils.byteToInt(pokemonBytes[29]);
        int move1Index = ByteUtils.byteToInt(pokemonBytes[31]);
        int move2Index = ByteUtils.byteToInt(pokemonBytes[32]);
        int move3Index = ByteUtils.byteToInt(pokemonBytes[33]);
        int move4Index = ByteUtils.byteToInt(pokemonBytes[34]);

        return new Pokemon(
                decode(Arrays.copyOfRange(pokemonBytes, 12, 22)),
                new Specie(specieIndex, sprite, Data.pokemons[specieIndex]),
                ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 24, 25 + 1)),
                ByteUtils.byteToInt(pokemonBytes[26]),
                pokemonBytes[27] == 0x04,
                pokemonBytes[27] == 0x07,
                pokemonBytes[27] == 0x10,
                pokemonBytes[27] == 0x20,
                pokemonBytes[27] == 0x40,
                new Type(type1Index, Data.types[type1Index]),
                new Type(type2Index, Data.types[type2Index]),
                ByteUtils.byteToInt(pokemonBytes[30]),
                new Move(move1Index, Data.moves[move1Index], pokemonBytes[52]),
                new Move(move2Index, Data.moves[move2Index], pokemonBytes[53]),
                new Move(move3Index, Data.moves[move3Index], pokemonBytes[54]),
                new Move(move4Index, Data.moves[move4Index], pokemonBytes[55]),
                ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 35, 36 + 1)),
                decode(Arrays.copyOfRange(pokemonBytes, 1, 11)),
                ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 37, 39 + 1)),
                ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 40, 41 + 1)),
                ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 42, 43 + 1)),
                ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 44, 45 + 1)),
                ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 46, 47 + 1)),
                ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 48, 49 + 1)),
                -1,
                isBox ? -1 : ByteUtils.byteToInt(pokemonBytes[50]) >> 4,
                isBox ? -1 : ByteUtils.byteToInt(pokemonBytes[50]) & 15,
                isBox ? -1 : ByteUtils.byteToInt(pokemonBytes[51]) >> 4,
                isBox ? -1 : ByteUtils.byteToInt(pokemonBytes[51]) & 15,
                isBox ? -1 : ByteUtils.byteToInt(pokemonBytes[56]),
                isBox ? -1 : ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 57, 58 + 1)),
                isBox ? -1 : ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 59, 60 + 1)),
                isBox ? -1 : ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 61, 62 + 1)),
                isBox ? -1 : ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 63, 64 + 1)),
                isBox ? -1 : ByteUtils.bytesToInt(Arrays.copyOfRange(pokemonBytes, 65, 66 + 1))
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
                ByteUtils.bytesToInt(Arrays.copyOfRange(saveInMemory, 0x2605, 0x2605 + 0x2)),
                decode(Arrays.copyOfRange(saveInMemory, 0x2598, 0x2598 + 0xB)),
                decode(Arrays.copyOfRange(saveInMemory, 0x25F6, 0x25F6 + 0xB)),
                BCDUtils.BCDToDecimal(Arrays.copyOfRange(saveInMemory, 0x25F3, 0x25F3 + 0x3)),
                (int) BCDUtils.BCDToDecimal(Arrays.copyOfRange(saveInMemory, 0x2850, 0x2850 + 0x2)),
                TimeUtils.playedToMilliseconds(
                        ByteUtils.byteToInt(saveInMemory[0x2CED]) + ByteUtils.byteToInt(saveInMemory[0x2CEE]) * 0x100,
                        ByteUtils.byteToInt(saveInMemory[0x2CEF]),
                        ByteUtils.byteToInt(saveInMemory[0x2CF0])
                ),
                generateItemContainer(0x25C9, 19),
                generateItemContainer(0x27E6, 49),
                generatePartyPokemons(),
                generatePcPokemons());
    }
}
