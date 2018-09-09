package types.gen1;

import models.Item;
import models.Player;
import models.Pokemon;
import utils.BCDUtils;
import utils.ByteUtils;
import utils.TimeUtils;

import java.util.Arrays;
import java.util.List;

public class Gen1Saver {
    private byte[] encode(String string) {
        final StringBuilder encoded = new StringBuilder();
        final List<String> tempList = Arrays.asList(Data.encodingTable);
        for (char c : string.toCharArray()) {
            encoded.append(String.format("%02X", tempList.indexOf(String.valueOf(c))));
        }
        return ByteUtils.hexStringToByteArray(encoded.toString());
    }

    private byte[] savePartyPokemons(byte[] saveInMemory, Pokemon[] pokemons) {
        for (int i = 0; i < 6; i++) {
            int offHex = 0x2F2C + 0x1 + i;
            int offOtName = 0x303C + 0xB * i;
            int offName = 0x307E + 0xB * i;
            int offData = 0x2F34 + 0x2C * i;

            saveInMemory[offHex] = 0x50;
            System.arraycopy(ByteUtils.genNullByteArray(0xB), 0, saveInMemory, offOtName, 0xB);
            System.arraycopy(ByteUtils.genNullByteArray(0xB), 0, saveInMemory, offName, 0xB);
            System.arraycopy(ByteUtils.genNullByteArray(44), 0, saveInMemory, offData, 44);

            if (i < pokemons.length) {
                byte[] encodedPokemon = encodePokemon(pokemons[i], false);
                saveInMemory[offHex] = encodedPokemon[0];
                System.arraycopy(encodedPokemon, 1, saveInMemory, offOtName, 0xB);
                System.arraycopy(encodedPokemon, 12, saveInMemory, offName, 0xB);
                System.arraycopy(encodedPokemon, 23, saveInMemory, offData, 44);
            }
        }

        return saveInMemory;
    }

    private byte[] savePcPokemons(byte[] saveInMemory, Pokemon[] pokemons) {
        int currentBox = (saveInMemory[0x284C] & 127) + 1;

        for (int i = 0; i < 12; i++) {
            int offset;
            if (i == currentBox)
                offset = 0x30C0;
            else
                offset = 0x4000 + i / 6 * 8192 + 1122 * (i % 6);

            for (int j = 0; j < 20; j++) {
                int offHex = offset + 0x1 + j;
                int offOtName = offset + 682 + 0xB * j;
                int offName = offset + 902 + 0xB * j;
                int offData = offset + 22 + 0x21 * j;

                saveInMemory[offHex] = 0x50;
                System.arraycopy(ByteUtils.genNullByteArray(0xB), 0, saveInMemory, offOtName, 0xB);
                System.arraycopy(ByteUtils.genNullByteArray(0xB), 0, saveInMemory, offName, 0xB);
                System.arraycopy(ByteUtils.genNullByteArray(33), 0, saveInMemory, offData, 33);

                if (pokemons[20 * i + j] != null) {
                    byte[] encodedPokemon = encodePokemon(pokemons[20 * i + j], true);
                    saveInMemory[offHex] = encodedPokemon[0];
                    System.arraycopy(encodedPokemon, 1, saveInMemory, offOtName, 0xB);
                    System.arraycopy(encodedPokemon, 12, saveInMemory, offName, 0xB);
                    System.arraycopy(encodedPokemon, 23, saveInMemory, offData, 33);
                }
            }
        }

        return saveInMemory;
    }

    public byte[] encodePokemon(Pokemon pokemon, boolean isBox) {
        int size = isBox ? 56 : 67;
        byte[] encodedPokemon = new byte[size];

        byte[] name = encode(pokemon.getNickname());
        byte[] currentHp = ByteUtils.intToBytes(pokemon.getCurrentHp(), 0x2);
        byte[] otId = ByteUtils.intToBytes(pokemon.getOtId(), 0x2);
        byte[] otName = encode(pokemon.getOtName());
        byte[] exp = ByteUtils.intToBytes((int) pokemon.getExp(), 0x3);
        byte[] hpEv = ByteUtils.intToBytes(pokemon.getHpEv(), 0x2);
        byte[] attackEv = ByteUtils.intToBytes(pokemon.getAttackEv(), 0x2);
        byte[] defenseEv = ByteUtils.intToBytes(pokemon.getDefenseEv(), 0x2);
        byte[] speedEv = ByteUtils.intToBytes(pokemon.getSpeedEv(), 0x2);
        byte[] specialEv = ByteUtils.intToBytes(pokemon.getSpecialEv(), 0x2);

        encodedPokemon[0] = ByteUtils.intToByte(pokemon.getSpecie().getIndex());
        encodedPokemon[23] = ByteUtils.intToByte(pokemon.getSpecie().getSprite());
        encodedPokemon[28] = ByteUtils.intToByte(pokemon.getType1().getIndex());
        encodedPokemon[29] = ByteUtils.intToByte(pokemon.getType2().getIndex());
        encodedPokemon[31] = ByteUtils.intToByte(pokemon.getMove1().getIndex());
        encodedPokemon[32] = ByteUtils.intToByte(pokemon.getMove2().getIndex());
        encodedPokemon[33] = ByteUtils.intToByte(pokemon.getMove3().getIndex());
        encodedPokemon[34] = ByteUtils.intToByte(pokemon.getMove4().getIndex());
        encodedPokemon[26] = ByteUtils.intToByte(pokemon.getLevelRepr());

        byte status = 0x00;
        if (pokemon.isAsleep())
            status = 0x04;
        else if (pokemon.isPoisoned())
            status = 0x07;
        else if (pokemon.isBurned())
            status = 0x10;
        else if (pokemon.isFrozen())
            status = 0x20;
        else if (pokemon.isParalyzed())
            status = 0x40;
        encodedPokemon[27] = status;
        encodedPokemon[30] = ByteUtils.intToByte(pokemon.getCatchRate());

        System.arraycopy(ByteUtils.genNullByteArray(0xB), 0, encodedPokemon, 12, 0xB);
        System.arraycopy(name, 0, encodedPokemon, 12, name.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 24, 0x2);
        System.arraycopy(currentHp, 0, encodedPokemon, 24, currentHp.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 35, 0x2);
        System.arraycopy(otId, 0, encodedPokemon, 35, otId.length);

        System.arraycopy(ByteUtils.genNullByteArray(0xB), 0, encodedPokemon, 1, 0xB);
        System.arraycopy(otName, 0, encodedPokemon, 1, otName.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x3), 0, encodedPokemon, 37, 0x3);
        System.arraycopy(exp, 0, encodedPokemon, 37, exp.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 40, 0x2);
        System.arraycopy(hpEv, 0, encodedPokemon, 40, hpEv.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 42, 0x2);
        System.arraycopy(attackEv, 0, encodedPokemon, 42, attackEv.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 44, 0x2);
        System.arraycopy(defenseEv, 0, encodedPokemon, 44, defenseEv.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 46, 0x2);
        System.arraycopy(speedEv, 0, encodedPokemon, 46, speedEv.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 48, 0x2);
        System.arraycopy(specialEv, 0, encodedPokemon, 48, specialEv.length);

        if (!isBox) {
            int attackIv = encodedPokemon[50] & 15;
            attackIv += pokemon.getAttackIv() << 4;
            encodedPokemon[50] = ByteUtils.intToByte(attackIv);

            int defenseIv = encodedPokemon[50] & 240;
            defenseIv += pokemon.getDefenseIv() & 15;
            encodedPokemon[50] = ByteUtils.intToByte(defenseIv);

            int speedIv = encodedPokemon[51] & 15;
            speedIv += pokemon.getSpeedIv() << 4;
            encodedPokemon[51] = ByteUtils.intToByte(speedIv);

            int specialIv = encodedPokemon[51] & 240;
            specialIv += pokemon.getSpecialIv() & 15;
            encodedPokemon[51] = ByteUtils.intToByte(specialIv);

            encodedPokemon[56] = ByteUtils.intToByte(pokemon.getCurrentLevel());

            byte[] maxHp = ByteUtils.intToBytes(pokemon.getMaxHp(), 0x2);
            byte[] attack = ByteUtils.intToBytes(pokemon.getAttack(), 0x2);
            byte[] defense = ByteUtils.intToBytes(pokemon.getDefense(), 0x2);
            byte[] speed = ByteUtils.intToBytes(pokemon.getSpeed(), 0x2);
            byte[] special = ByteUtils.intToBytes(pokemon.getSpecial(), 0x2);

            System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 57, 0x2);
            System.arraycopy(maxHp, 0, encodedPokemon, 57, maxHp.length);

            System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 59, 0x2);
            System.arraycopy(attack, 0, encodedPokemon, 59, attack.length);

            System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 61, 0x2);
            System.arraycopy(defense, 0, encodedPokemon, 61, defense.length);

            System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 63, 0x2);
            System.arraycopy(speed, 0, encodedPokemon, 63, speed.length);

            System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, encodedPokemon, 65, 0x2);
            System.arraycopy(special, 0, encodedPokemon, 65, special.length);
        }

        return encodedPokemon;
    }

    private byte[] saveItems(byte[] saveInMemory, Item[] items, boolean isBox) {
        int offset = isBox ? 0x27E6 : 0x25C9;
        int totalCount = isBox ? 50 : 20;

        saveInMemory[offset] = ByteUtils.intToByte(items.length);
        System.arraycopy(ByteUtils.genNullByteArray(0x01 + 2 * totalCount), 0, saveInMemory, offset + 0x1, 0x01 + 2 * totalCount);
        for (int i = 0; i < totalCount; i++) {
            if (items[i] == null)
                break;
            byte itemIndex = ByteUtils.intToByte(items[i].getIndex());
            byte quantity = ByteUtils.intToByte(items[i].getQuantity());

            saveInMemory[offset + 0x1 + 0x2 * i] = itemIndex;
            saveInMemory[offset + 0x2 + 0x2 * i] = quantity;
        }

        return saveInMemory;
    }

    private byte[] saveTrainerParams(byte[] saveInMemory, Player player) {
        byte[] id = ByteUtils.intToBytes((int) player.getId(), 2);
        byte[] name = encode(player.getName());
        byte[] rivalName = encode(player.getRivalName());
        byte[] money = BCDUtils.decimalToBCD(player.getMoney(), 3);
        byte[] casinoChips = BCDUtils.decimalToBCD(player.getCasinoChips());
        byte secondsPlayed = ByteUtils.intToByte(TimeUtils.millisToSeconds(player.getTimePlayedInMillis()));
        byte minutesPlayed = ByteUtils.intToByte(TimeUtils.millisToMinutes(player.getTimePlayedInMillis()));
        byte[] hoursPlayed = ByteUtils.reverseByteArray(ByteUtils.intToBytes(TimeUtils.millisToHours(player.getTimePlayedInMillis()), 2));

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, saveInMemory, 0x2605, 0x2);
        System.arraycopy(id, 0, saveInMemory, 0x2605, id.length);

        System.arraycopy(ByteUtils.genNullByteArray(0xB), 0, saveInMemory, 0x2598, 0xB);
        System.arraycopy(name, 0, saveInMemory, 0x2598, name.length);

        System.arraycopy(ByteUtils.genNullByteArray(0xB), 0, saveInMemory, 0x25F6, 0xB);
        System.arraycopy(rivalName, 0, saveInMemory, 0x25F6, rivalName.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x3), 0, saveInMemory, 0x25F3, 0x3);
        System.arraycopy(money, 0, saveInMemory, 0x25F3, money.length);

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, saveInMemory, 0x2850, 0x2);
        System.arraycopy(casinoChips, 0, saveInMemory, 0x2850, casinoChips.length);

        saveInMemory[0x2CF0] = secondsPlayed;

        saveInMemory[0x2CEF] = minutesPlayed;

        System.arraycopy(ByteUtils.genNullByteArray(0x2), 0, saveInMemory, 0x2CED, 0x2);
        System.arraycopy(hoursPlayed, 0, saveInMemory, 0x2CED, hoursPlayed.length);

        return saveInMemory;
    }

    private byte[] setChecksum(byte[] saveInMemory) {
        int start = 0x2598;
        int checksum = 255;
        for (int i = 0; i < 0xF8B; i++) {
            checksum -= ByteUtils.byteToInt(saveInMemory[i + start]);
            checksum &= 255;
        }
        saveInMemory[0x3523] = ByteUtils.intToByte(checksum);

        return saveInMemory;
    }

    public byte[] applyChanges(Gen1Loader gen1Loader, Player player) {
        saveTrainerParams(gen1Loader.getSaveInMemory(), player);
        saveItems(gen1Loader.getSaveInMemory(), player.getBag(), false);
        saveItems(gen1Loader.getSaveInMemory(), player.getPcItemBox(), true);
        savePartyPokemons(gen1Loader.getSaveInMemory(), player.getPartyPokemon());
        savePcPokemons(gen1Loader.getSaveInMemory(), player.getPcPokemon());
        setChecksum(gen1Loader.getSaveInMemory());

        return gen1Loader.getSaveInMemory();
    }
}
