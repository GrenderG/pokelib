import com.google.gson.Gson;
import types.gen1.Gen1Loader;
import models.Player;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main (String args[]) {
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
    }
}
