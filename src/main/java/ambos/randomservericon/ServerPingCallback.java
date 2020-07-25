package ambos.randomservericon;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;

public interface ServerPingCallback {
    Event<ServerPingCallback> EVENT = EventFactory.createArrayBacked(ServerPingCallback.class,
            (listeners) -> (server) -> {
                for (ServerPingCallback listener : listeners) {
                    ActionResult result = listener.interact(server);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(MinecraftServer server);
}
