package random.beasts.api.world.biome.underground;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;

public class ChunkCorePlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{ClassTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    public static class ClassTransformer implements IClassTransformer {

        @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {
            if (transformedName.equals("net.minecraft.world.chunk.Chunk")) {
                try {
                    ClassNode cls = new ClassNode();
                    ClassReader reader = new ClassReader(transformedName);
                    reader.accept(cls, 0);

                    cls.methods.stream().filter(v -> v.name.equals("getBiome") || v.name.equals("func_177411_a")).findAny().ifPresent(method -> {
                        InsnList instructions = method.instructions;
                        LabelNode label = (LabelNode) instructions.getFirst();
                        //instructions.insert(label, new FrameNode(Opcodes.F_APPEND, 4, new Object[] { 7, Type.getInternalName(BlockPos.class), Type.getInternalName(BiomeProvider.class), Type.getInternalName(Biome.class) }, 0, null));
                        instructions.insertBefore(label, new InsnNode(Opcodes.ARETURN));
                        instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 3));
                        instructions.insertBefore(instructions.getFirst(), new JumpInsnNode(Opcodes.IFNULL, label));
                        instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 3));
                        instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ASTORE, 3));
                        instructions.insertBefore(instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(UndergroundBiome.class), "getBiome", Type.getMethodDescriptor(Type.getType(Biome.class), Type.getType(BlockPos.class), Type.getType(Object.class)), false));
                        instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 0));
                        instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 1));
                    });
                    ClassWriter writer = new ClassWriter(reader, 0);
                    cls.accept(writer);
                    //return writer.toByteArray();
                } catch (IOException e) {
                    LogManager.getLogger().error("Failed to transform Chunk class, underground biomes won't work.", e);
                }
            }
            return basicClass;
        }
    }
}
