function initializeCoreMod() {
    return {
        'coremodmethod': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.world.gen.NoiseChunkGenerator',
                'methodName': 'makeBase',
                'methodDesc': '(Lnet/minecraft/world/IWorld;Lnet/minecraft/world/chunk/IChunk;)V'
            },
            'transformer': function (method) {
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
                var instructions = method.instructions;
                instructions.insertBefore(instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, "random/beasts/api/world/biome/underground/UndergroundGenerationEvents", "generate", "(Lnet/minecraft/world/IWorld;Lnet/minecraft/world/chunk/IChunk;Ljava/lang/Object;)V", false));
                instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 0));
                instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 2));
                instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 1));
                return method;
            }
        }
    };
}
