package user11681.prone.asm.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import user11681.prone.asm.access.ProneEntity;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Inject(method = "onPlayerConnected", at = @At("RETURN"))
    private void syncProneOnConnection(final ServerPlayerEntity player, final CallbackInfo info) {
        ((ProneEntity) player).prone_sync();
    }

    @Inject(method = "onPlayerChangeDimension", at = @At("RETURN"))
    private void syncProneOnDimensionChange(final ServerPlayerEntity player, final CallbackInfo info) {
        ((ProneEntity) player).prone_sync();
    }

    @Inject(method= "onPlayerRespawned", at = @At("RETURN"))
    private void syncProneOnRespawn(final ServerPlayerEntity player, final CallbackInfo info) {
        ((ProneEntity) player).prone_sync();
    }
}
