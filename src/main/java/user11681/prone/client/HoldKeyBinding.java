package user11681.prone.client;

import org.lwjgl.glfw.GLFW;

public class HoldKeyBinding extends ProneKeyBinding {
    public HoldKeyBinding() {
        super("key.prone.hold", GLFW.GLFW_KEY_LEFT_ALT, "prone");
    }

    @Override
    public void setPressed(final boolean pressed) {
        if (pressed ^ this.isPressed()) {
            this.toggleProne();
        }

        super.setPressed(pressed);
    }
}
