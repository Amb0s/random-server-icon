package ambos.randomservericon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomServerIcon extends JavaPlugin implements Listener {
    private ArrayList<File> icons;

    @Override
    public void onEnable(){
        File directory = getDataFolder();

        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            icons = listIcons(directory);
        } catch (IOException e) {
            getLogger().severe("Error while getting server icons!");
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    private ArrayList<File> listIcons(File directory) throws IOException {
        ArrayList<File> icons = new ArrayList<>();
        File[] files = directory.listFiles();
        String type;

        for (File file : files) {
            type = Files.probeContentType(Paths.get(file.getPath()));

            if (!file.isDirectory() && type.equals("image/png")) {
                icons.add(file);
            }
        }

        return icons;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent serverListPingEvent) {
        Random random = new Random();
        try {
            serverListPingEvent.setServerIcon(Bukkit.loadServerIcon(icons.get(random.nextInt(icons.size()))));
        } catch (Exception e) {
            getLogger().severe("Error while setting server icon!");
        }
    }
}
