package user11681.prone.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import user11681.prone.asm.access.ProneEntity;

public abstract class ProneKeyBinding extends KeyBinding {
    public ProneKeyBinding(final String translationKey, final int code, final String category) {
        super(translationKey, code, category);
    }

    protected final void toggleProne() {
        final ProneEntity player = (ProneEntity) MinecraftClient.getInstance().player;

        assert player != null;

        player.prone_toggleProne();
        player.prone_sync();
    }
}
