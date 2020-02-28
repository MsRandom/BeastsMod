package random.beasts.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import random.beasts.api.main.BeastsUtils;

public class BeastsFood extends Item {
    public BeastsFood(String name, Item.Properties properties) {
        super(properties);
        BeastsUtils.addToRegistry(this, name);
    }

    public BeastsFood(String name, int amount, float saturation) {
        this(name, new Item.Properties().group(BeastsUtils.getRegistryTab()).food(new Food.Builder().hunger(amount).saturation(saturation).build()));
        BeastsUtils.addToRegistry(this, name);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity) onFoodEaten(stack, worldIn, (PlayerEntity) entityLiving);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, PlayerEntity player) {
    }
}
