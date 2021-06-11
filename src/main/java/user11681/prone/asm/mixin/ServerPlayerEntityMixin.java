package user11681.prone.asm.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import user11681.prone.asm.access.ProneEntity;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ProneEntity {
    @Inject(method = "copyFrom", at = @At("RETURN"))
    private void copyProne(final ServerPlayerEntity oldPlayer, final boolean alive, final CallbackInfo info) {
        this.prone_setProne(((ProneEntity) oldPlayer).prone_isProne());
    }
}
