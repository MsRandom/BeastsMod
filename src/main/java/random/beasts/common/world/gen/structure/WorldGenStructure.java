package random.beasts.common.world.gen.structure;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import random.beasts.common.main.BeastsReference;

public class WorldGenStructure extends WorldGenerator
{
	public static String structureName;
	public static final WorldServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
	
	public static PlacementSettings settings = (new PlacementSettings()).setChunk(null).setIgnoreEntities(false)
			.setIgnoreStructureBlock(false).setMirror(Mirror.NONE);
	
	public WorldGenStructure(String name)
	{
		this.structureName = name;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) 
	{
		this.generateStructure(world, pos);
		return true;
	}
	
	public static void generateStructure(World world, BlockPos pos)
	{
		MinecraftServer mcServer = world.getMinecraftServer();
		TemplateManager manager = worldServer.getStructureTemplateManager();
		ResourceLocation location = new ResourceLocation(BeastsReference.ID, structureName);
		Template template = manager.get(mcServer, location);
		int rand = new Random().nextInt(4);
		
		if(template != null)
		{
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
			
			switch(rand)
			{
				case 0:
					settings = settings.setRotation(Rotation.NONE);
					break;
				case 1:
					settings = settings.setRotation(Rotation.CLOCKWISE_180);
					break;
				case 2:
					settings = settings.setRotation(Rotation.CLOCKWISE_90);
					break;
				case 3:
					settings = settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
					break;
			}
			
			template.addBlocksToWorldChunk(world, pos, settings);
		}
	}
}
