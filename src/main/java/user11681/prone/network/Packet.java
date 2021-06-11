package user11681.prone.network;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;

public abstract class Packet implements PacketConsumer {
    @Override
    public void accept(final PacketContext context, final PacketByteBuf buffer) {
        final PacketByteBuf copy = new PacketByteBuf(buffer.copy());

        context.getTaskQueue().execute(() -> this.execute(context, copy));
    }

    protected abstract void execute(final PacketContext context, final PacketByteBuf buffer);
}
