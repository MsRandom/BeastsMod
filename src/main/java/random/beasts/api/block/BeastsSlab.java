package random.beasts.api.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import random.beasts.api.main.BeastsUtils;

import java.util.function.Function;

public class BeastsSlab {
    public static final PropertyEnum<FakeVariant> VARIANT = PropertyEnum.create("variant", FakeVariant.class);
    public SlabType full;
    public SlabType half;

    public BeastsSlab(String name, Function<Boolean, ? extends SlabType> constructor) {
        this.full = constructor.apply(true);
        this.half = constructor.apply(false);
        BeastsUtils.addToRegistry(full, "double_" + name + "_slab", null);
        BeastsUtils.addToRegistry(half, name + "_slab", block -> new ItemSlab(block, half, full));
    }

    public BeastsSlab(String name) {
        this(name, SlabType::new);
    }

    public enum FakeVariant implements IStringSerializable {
        DEFAULT;

        public String getName() {
            return "default";
        }
    }

    public static class SlabType extends BlockSlab {
        private boolean full;

        SlabType(boolean full) {
            super(Material.WOOD);
            this.setHardness(2.0F);
            this.setResistance(5.0F);
            this.full = full;
        }

        public IBlockState getStateFromMeta(int meta) {
            IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, FakeVariant.DEFAULT);
            if (!this.isDouble())
                iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
            return iblockstate;
        }

        public int getMetaFromState(IBlockState state) {
            int i = 0;
            if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) i |= 8;
            return i;
        }

        protected BlockStateContainer createBlockState() {
            return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
        }

        @Override
        public String getUnlocalizedName(int meta) {
            return getUnlocalizedName();
        }

        @Override
        public boolean isDouble() {
            return full;
        }

        //yay mc, definitely the best way of doing this...

        @Override
        public IProperty<?> getVariantProperty() {
            return VARIANT;
        }

        @Override
        public Comparable<?> getTypeForItem(ItemStack stack) {
            return FakeVariant.DEFAULT;
        }
    }
}