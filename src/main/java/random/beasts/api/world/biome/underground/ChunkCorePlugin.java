package random.beasts.api.world.biome.underground;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;

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
                            /*for (int i = 0; i < instructions.size(); i++) {
                                System.out.println(instructions.get(i));
                            }
                            LabelNode label = (LabelNode)instructions.getFirst();
                            instructions.insertBefore(label, new InsnNode(Opcodes.ARETURN));
                            instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 3));
                            instructions.insertBefore(instructions.getFirst(), new JumpInsnNode(Opcodes.IFNULL, label));
                            instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 3));
                            label = new LabelNode();
                            instructions.insertBefore(instructions.getFirst(), new LineNumberNode(1297, label));
                            instructions.insertBefore(instructions.getFirst(), label);
                            */
                        //instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ASTORE, 3));
                        //instructions.insertBefore(instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, "random/beasts/api/world/biome/UndergroundBiome", "getBiome", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/Chunk;)Lnet/minecraft/world/biome/Biome", false));
                        //instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 0));
                        //instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 1));
                            /*
                            System.out.println("TRANSFORMED");
                            for (int i = 0; i < instructions.size(); i++) {
                                System.out.println(instructions.get(i));
                            }*/
                    });
                    ClassWriter writer = new ClassWriter(reader, 0);
                    cls.accept(writer);
                    //FileOutputStream w = new FileOutputStream("/home/random/IdeaProjects/BeastsMod/c.class");
                    //w.write(writer.toByteArray());
                    //file:///home/random/Downloads/Programs/mods/RTG-1.12.2-6.1.0.0-snapshot.1-deobf.jarw.close();
                    return writer.toByteArray();
                } catch (IOException e) {
                    LogManager.getLogger().error("Failed to transform Chunk class, underground biomes won't work.", e);
                }
            }
            return basicClass;
        }
    }
}
