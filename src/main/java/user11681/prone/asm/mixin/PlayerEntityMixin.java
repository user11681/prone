package user11681.prone.asm.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import user11681.prone.asm.access.ProneEntity;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(final EntityType<? extends LivingEntity> entityType, final World world) {
        super(entityType, world);
    }

    @Redirect(method = "travel", at = @At(value = "FIELD", target = "Lnet/minecraft/util/math/Vec3d;y:D"))
    public double fixJump(final Vec3d rotation) {
        return ((ProneEntity) this).prone_isProne() && this.jumping && !this.isSubmergedInWater() ? 0 : rotation.y;
    }
}
