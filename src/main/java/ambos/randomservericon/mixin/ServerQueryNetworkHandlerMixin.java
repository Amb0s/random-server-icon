package ambos.randomservericon.mixin;


import ambos.randomservericon.ServerPingCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerQueryNetworkHandler;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerQueryNetworkHandler.class)
public class ServerQueryNetworkHandlerMixin {
	@Shadow @Final
	private MinecraftServer server;

	@Inject(at = @At("RETURN"), method = "onPing")
	private void onPing(CallbackInfo info) {
		ActionResult result = ServerPingCallback.EVENT.invoker().interact(server);

		if (result == ActionResult.FAIL) {
			info.cancel();
		}
	}
}
