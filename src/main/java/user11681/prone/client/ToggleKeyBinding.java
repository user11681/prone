package user11681.prone.client;

import org.lwjgl.glfw.GLFW;

public class ToggleKeyBinding extends ProneKeyBinding {
    public ToggleKeyBinding() {
        super("key.prone.toggle", GLFW.GLFW_KEY_C, "prone");
    }

    @Override
    public void setPressed(final boolean pressed) {
        if (!this.isPressed() && pressed) {
            this.toggleProne();
        }

        super.setPressed(pressed);
    }
}
