package ambos.randomservericon;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.ActionResult;

public class RandomServerIcon implements ModInitializer {
	@Override
	public void onInitialize() {
		ServerPingCallback.EVENT.register((server) -> {
			((MinecraftServerAccess) server).setServerIcon();

			return ActionResult.PASS;
		});
	}
}
