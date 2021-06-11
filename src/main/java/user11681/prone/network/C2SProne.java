package user11681.prone.network;

import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import user11681.prone.Prone;
import user11681.prone.asm.access.ProneEntity;

public class C2SProne extends Packet {
    public static final Identifier ID = new Identifier(Prone.MOD_ID, "server_prone");

    @Override
    public void execute(final PacketContext context, final PacketByteBuf buffer) {
        final PlayerEntity sender = context.getPlayer();

        ((ProneEntity) sender).prone_toggleProne();
    }
}
