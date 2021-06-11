package user11681.prone;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import user11681.prone.client.HoldKeyBinding;
import user11681.prone.client.ToggleKeyBinding;
import user11681.prone.command.ProneCommand;
import user11681.prone.network.C2SProne;
import user11681.prone.network.S2CProne;

@EnvironmentInterface(value = EnvType.CLIENT, itf = ClientModInitializer.class)
public class Prone implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "prone";

    @Override
    public void onInitialize() {
        ServerSidePacketRegistry.INSTANCE.register(C2SProne.ID, new C2SProne());
        CommandRegistrationCallback.EVENT.register(ProneCommand::register);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(S2CProne.ID, new S2CProne());
        KeyBindingHelper.registerKeyBinding(new HoldKeyBinding());
        KeyBindingHelper.registerKeyBinding(new ToggleKeyBinding());
    }
}
