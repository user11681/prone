package user11681.prone.asm.mixin;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.prone.Prone;
import user11681.prone.asm.access.ProneEntity;
import user11681.prone.network.C2SProne;
import user11681.prone.network.S2CProne;

@Mixin(Entity.class)
public abstract class EntityMixin implements ProneEntity {
    @Unique
    private static final String NBT_KEY = String.format("%s:prone", Prone.MOD_ID);

    @Unique
    private boolean prone;

    @ModifyVariable(method = "setSwimming", at = @At("LOAD"))
    private boolean setSwimming(final boolean swimming) {
        return swimming || this.prone_isProne();
    }

    @Inject(method = "shouldSpawnSprintingParticles", at = @At("RETURN"), cancellable = true)
    private void disableSprintingParticles(final CallbackInfoReturnable<Boolean> info) {
        if (this.prone_isProne()) {
            info.setReturnValue(false);
        }
    }

    @Inject(method = "copyFrom", at = @At("RETURN"))
    private void copyProne(final Entity original, final CallbackInfo info) {
        this.prone_setProne(((ProneEntity) original).prone_isProne());
    }

    @Inject(method = "fromTag", at = @At("RETURN"))
    private void deserialize(final CompoundTag tag, final CallbackInfo info) {
        this.prone_setProne(tag.getBoolean(NBT_KEY));
    }

    @Inject(method = "toTag", at = @At("RETURN"))
    private void serialize(final CompoundTag tag, final CallbackInfoReturnable<CompoundTag> info) {
        tag.putBoolean(NBT_KEY, this.prone_isProne());
    }

    @Override
    @Unique
    public boolean prone_isActuallySwimming() {
        return ((Entity) (Object) this).isSwimming() && (!this.prone_isProne() || ((Entity) (Object) this).isSubmergedInWater());
    }

    @Override
    @Unique
    public boolean prone_isProne() {
        return this.prone;
    }

    @Override
    @Unique
    public void prone_setProne(final boolean prone) {
        this.prone = prone;
    }

    @Override
    @Unique
    public void prone_sync() {
        if (((Entity) (Object) this).world.isClient) {
            ClientSidePacketRegistry.INSTANCE.sendToServer(C2SProne.ID, new PacketByteBuf(Unpooled.buffer()));
        } else {
            for (final PlayerEntity player : ((Entity) (Object) this).world.getPlayers()) {
                ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, S2CProne.ID,
                    new PacketByteBuf(Unpooled.buffer().writeBoolean(((ProneEntity) (Entity) (Object) this).prone_isProne())).writeVarInt(((Entity) (Object) this).getEntityId())
                );
            }
        }
    }
}
