package user11681.prone.asm;

import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class ProneMixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(final String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {
        if (mixinClassName.endsWith("ClientPlayerEntityMixin")) {
            final boolean development = FabricLoader.getInstance().isDevelopmentEnvironment();
            final String tickMovement = development ? "tickMovement" : "method_6007";

            for (final MethodNode method : targetClass.methods) {
                if (method.name.equals(tickMovement)) {
                    final String isTouchingWater = development ? "isTouchingWater" : "method_5799";
                    AbstractInsnNode instruction = method.instructions.getFirst();
                    int isTouchingWaterCount = 0;

                    while (instruction != null) {
                        if (instruction.getOpcode() == Opcodes.INVOKEVIRTUAL && ((MethodInsnNode) instruction).name.equals(isTouchingWater)) {
                            if (isTouchingWaterCount == 2) {
                                final InsnList insertion = new InsnList();

                                insertion.add(new VarInsnNode(Opcodes.ALOAD, 0));
                                insertion.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, targetClassName.replace('.', '/'), development ? "isInSwimmingPose" : "method_20232", "()Z", false));
                                insertion.add(new JumpInsnNode(Opcodes.IFNE, ((JumpInsnNode) (instruction = instruction.getNext())).label));

                                method.instructions.insert(instruction, insertion);

                                return;
                            } else {
                                ++isTouchingWaterCount;
                            }
                        }

                        instruction = instruction.getNext();
                    }
                }
            }
        }
    }

    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {}
}
