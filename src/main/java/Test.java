import com.google.gson.Gson;
import models.Player;
import types.gen1.Gen1Loader;
import types.gen1.Gen1Saver;
import utils.TimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String args[]) {
        Gen1Loader loader = new Gen1Loader();
        loader.loadSaveInMemory(new File("/home/grender/Desktop/pk/pktest/pkr.sav"));
        Player player = loader.getPlayer();
        System.out.println(new Gson().toJson(player));
        System.out.println(player.getId());
        System.out.println(player.getName());
        System.out.println(player.getRivalName());
        System.out.println(player.getMoney());
        System.out.println(player.getCasinoChips());
        System.out.println(TimeUnit.MILLISECONDS.toHours(player.getTimePlayedInMillis()));

        //player.setId(7331);
        player.setMoney(1337);
        player.setName("Holapio");
        player.setTimePlayedInMillis(TimeUtils.playedToMilliseconds(255, 16, 51));
        player.getBag()[5].setIndex(10);
        player.getBag()[5].setQuantity(13);
        player.getPartyPokemon()[0].setNickname("HOLA");
        player.getPcPokemon()[0].setNickname("PORFA");

        try {
            byte[] ns = new Gen1Saver().applyChanges(loader, player);
            FileOutputStream fos = new FileOutputStream(new File("/home/grender/Desktop/pk/pktest/npkr.sav"));
            fos.write(ns, 0, ns.length);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
