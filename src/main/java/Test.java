import loaders.gen1.Gen1Loader;
import models.Player;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main (String args[]) {
        Gen1Loader loader = new Gen1Loader();
        loader.loadSaveInMemory(new File("pkr.sav"));
        Player player = loader.getPlayer();

        System.out.println(player.getId());
        System.out.println(player.getName());
        System.out.println(player.getRivalName());
        System.out.println(player.getMoney());
        System.out.println(player.getCasinoChips());
        System.out.println(TimeUnit.MILLISECONDS.toHours(player.getTimePlayedInMillis()));
    }
}
