package ambos.randomservericon.mixin;

import ambos.randomservericon.MinecraftServerAccess;
import net.minecraft.server.MinecraftServer;

import net.minecraft.server.ServerMetadata;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerAccess {
    @Shadow @Final
    private ServerMetadata metadata;

    @Shadow
    private static final Logger LOGGER = LogManager.getLogger();

    @Shadow @Final
    protected LevelStorage.Session session;

    @Shadow
    private void setFavicon(ServerMetadata metadata) {};

    public void setServerIcon() {
        setFavicon(metadata);
    }

    @Redirect(method = "setFavicon", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getFile(Ljava/lang/String;)Ljava/io/File;"))
    private File getRandomIcon(MinecraftServer server, String s) {
        Random rand = new Random();
        String type = null;
        ArrayList<File> icons = new ArrayList<>();
        File[] files = new File(".").listFiles();

        for (File file : files) {
            try {
                type = Files.probeContentType(Paths.get(file.getPath()));
            } catch (IOException e) {
                LOGGER.error("Couldn't access file", e);
            }

            if (type != null && !file.isDirectory() && type.equals("image/png") ) {
                icons.add(file);
            }
        }

        return icons.size() == 0 ? session.getIconFile() : icons.get(rand.nextInt(icons.size()));
    }
}
