package random.beasts.common.world.gen;

import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import random.beasts.api.world.IBeastsGenerator;
import random.beasts.common.BeastsMod;

import java.util.Random;

public class WorldGenStructure implements IBeastsGenerator {
    private static PlacementSettings settings = (new PlacementSettings()).setIgnoreEntities(false).setMirror(Mirror.NONE);
    private ResourceLocation structureName;

    public WorldGenStructure(String name) {
        this.structureName = new ResourceLocation(BeastsMod.MOD_ID, name);
    }

    @Override
    public boolean generate(IWorld world, Random rand, BlockPos pos) {
        if (world instanceof World) {
            MinecraftServer mcServer = ((World) world).getServer();
            if (mcServer != null) {
                TemplateManager manager = mcServer.getWorld(DimensionType.OVERWORLD).getStructureTemplateManager();
                Template template = manager.getTemplate(structureName);
                if (template != null) {
                    BlockState state = world.getBlockState(pos);
                    ((World) world).notifyBlockUpdate(pos, state, state, 3);
                    settings = settings.setRotation(Rotation.values()[rand.nextInt(4)]);
                    template.addBlocksToWorldChunk(world, pos, settings);
                    return true;
                }
            }
        }
        return false;
    }
}
