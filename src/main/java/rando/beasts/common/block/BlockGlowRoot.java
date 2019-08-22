package rando.beasts.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.utils.BeastsUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockGlowRoot extends Block {

    private boolean isTop;

    public BlockGlowRoot(String unloc, boolean b) {
        super(Material.GRASS, MapColor.GRASS);
        this.isTop = b;
        this.setSoundType(SoundType.PLANT);
        BeastsUtil.addToRegistry(this, unloc, false);
        this.setLightLevel(1.0F);
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if(isTop){
            return worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR;
        }
        return worldIn.getBlockState(pos.up()).getBlock() == BeastsBlocks.GLOW_ROOT_TOP;
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        this.checkAndDropBlock(worldIn, pos, state);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (isTop)
        {
            if (player.capabilities.isCreativeMode)
            {
                worldIn.setBlockToAir(pos.down());
            }
            else
            {
                worldIn.destroyBlock(pos.down(),true);
            }
        }
        else
        {
            worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    public boolean isReplaceable(World worldIn, BlockPos pos)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return isTop ? Item.getItemFromBlock(Blocks.AIR) : BeastsItems.GLOW_ROOT;
    }
}
