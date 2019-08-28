package rando.beasts.common.block;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.tileentity.TileEntityCoconut;

@SuppressWarnings("deprecation")
public class BlockCoconut extends BlockFalling implements ITileEntityProvider {

    private static final AxisAlignedBB AABB =  new AxisAlignedBB(0.25, 0, 0.25, 0.75, 0.2, 0.75);

    public BlockCoconut() {
        super(Material.WOOD);
        String name = "coconut";
        setUnlocalizedName(name);
        setRegistryName(name);
        hasTileEntity = true;
        BeastsBlocks.LIST.add(this);
    }

    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEntityCoconut();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Random rand = playerIn.getRNG();
        for(int i = 0; i < 4; ++i) worldIn.spawnParticle(EnumParticleTypes.BLOCK_DUST, hitX + rand.nextDouble(), hitY + rand.nextDouble(), hitZ + rand.nextDouble(), (rand.nextDouble() - 0.5D) * 0.5D, (rand.nextDouble() - 0.5D) * 0.5D, (rand.nextDouble() - 0.5D) * 0.5D, Block.REGISTRY.getIDForObject(Blocks.SAND));
        removedByPlayer(state, worldIn, pos, playerIn, true);
        spawnAsEntity(worldIn, pos, new ItemStack(BeastsItems.COCONUT));
        return true;
    }

    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
