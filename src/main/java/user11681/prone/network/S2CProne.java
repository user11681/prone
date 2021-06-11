package user11681.prone.network;

import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import user11681.prone.Prone;
import user11681.prone.asm.access.ProneEntity;

public class S2CProne extends Packet {
    public static final Identifier ID = new Identifier(Prone.MOD_ID, "client_prone");

    @Override
    public void execute(final PacketContext context, final PacketByteBuf buffer) {
        final boolean prone = buffer.readBoolean();
        final Entity entity = context.getPlayer().world.getEntityById(buffer.readVarInt());

        if (entity != null) {
            ((ProneEntity) entity).prone_setProne(prone);
        }
    }
}
