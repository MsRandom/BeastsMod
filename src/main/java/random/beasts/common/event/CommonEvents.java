package random.beasts.common.event;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import random.beasts.common.entity.monster.EntityBranchieBase;
import random.beasts.common.entity.passive.EntityPufferfishDog;
import random.beasts.common.entity.passive.EntityRabbitman;
import random.beasts.common.init.BeastsLootTables;
import random.beasts.common.main.BeastsReference;
import random.beasts.common.world.storage.loot.BeastsLootTable;

import java.util.Collection;
import java.util.function.Function;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class CommonEvents {

    @SubscribeEvent
    public static void addEntity(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityOcelot) {
            final EntityOcelot ocelot = (EntityOcelot) event.getEntity();
            if(ocelot != null) {
                ocelot.targetTasks.addTask(1, new EntityAITargetNonTamed<>(ocelot, EntityPufferfishDog.class, false, target -> target != null && target.getDistance(ocelot) < 32.0));
                ocelot.targetTasks.addTask(1, new EntityAITargetNonTamed<>(ocelot, EntityRabbitman.class, false, target -> target != null && target.getDistance(ocelot) < 32.0));
            }
        } else if (event.getEntity() instanceof EntityWolf) {
            final EntityWolf wolf = (EntityWolf) event.getEntity();
            if(wolf != null) wolf.targetTasks.addTask(1, new EntityAITargetNonTamed<>(wolf, EntityRabbitman.class, false, target -> target != null && target.getDistance(wolf) < 32.0));
        }
    }

    /*@SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player.world.isRemote)
            return;

        Vec3d vec3 = player.getPositionEyes(1.0F);
        Vec3d vec3a = player.getLook(1.0F);
        int distance = 14;
        Vec3d vec3b = vec3.addVector(vec3a.x * distance, vec3a.y * distance, vec3a.z * distance);

        Entity ee = null;
        List<Entity> list = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(distance + 1));
        double d0 = 0.0D;
        for (Entity entity1 : list) {
            if (entity1 != player) {
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3, vec3b);
                if (raytraceresult != null) {
                    double d1 = vec3.squareDistanceTo(raytraceresult.hitVec);
                    if (d1 < d0 || d0 == 0.0D) {
                        ee = entity1;
                        d0 = d1;
                    }
                }
            }
        }
        if(ee instanceof EntityPufferfishDog) BeastsTriggers.DISCOVER_PUFFERFISH_DOG.trigger((EntityPlayerMP) player);
    }*/
    
    @SubscribeEvent
    public static void loadLootTable(LootTableLoadEvent event) {
        BeastsLootTable table = BeastsLootTables.TABLES.get(event.getName().getResourcePath());
        if(table != null) event.getTable().getPool("main").addEntry(table.tableSupplier.get());
    }

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        Function<BlockEvent.BreakEvent, ? extends EntityBranchieBase> createFunc = null;
        IBlockState state = event.getState();
        Block block = state.getBlock();
        for (Collection<? extends Block> blocks : EntityBranchieBase.TYPES.keySet()) {
            if(blocks.contains(block)) {
                createFunc = EntityBranchieBase.TYPES.get(blocks);
                break;
            }
        }
        if(createFunc != null && !event.getWorld().isRemote && event.getPlayer().getRNG().nextBoolean() && event.getWorld().getBlockState(event.getPos().down()).getBlock() != block) {
            EntityBranchieBase entity = createFunc.apply(event);
            if(entity != null) {
                entity.scream();
                entity.setAttackTarget(event.getPlayer());
                event.getWorld().spawnEntity(entity);
            }
        }
    }

    @SubscribeEvent
    public static void attackLiving(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (!player.getActiveItemStack().isEmpty()) {
                ItemStack stack = player.getActiveItemStack();
                float damage = event.getAmount();
                if (damage > 5.0F && (stack.getItem() instanceof ItemShield && stack.getItem() != Items.SHIELD)) {
                    int i = Math.min(1 + (int) damage, stack.getMaxDamage() - stack.getItemDamage());
                    stack.damageItem(i, player);
                    if (stack.isEmpty() || stack.getItemDamage() >= stack.getMaxDamage()) {
                        if (stack.isEmpty()) {
                            EnumHand hand = player.getActiveHand();
                            ForgeEventFactory.onPlayerDestroyItem(player, stack, hand);
                            if (hand == EnumHand.MAIN_HAND)
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                            else player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
                        }

                        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 0.9f, 0.8F + player.world.rand.nextFloat() * 0.4F);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void craftItem(PlayerEvent.ItemCraftedEvent event) {
        /*if (event.crafting.getItem() == BeastsItems.COCONUT_JUICE) {
            for (AtomicInteger i = new AtomicInteger(); i.get() < event.craftMatrix.getSizeInventory(); i.getAndIncrement()) {
                ItemStack stack = event.craftMatrix.getStackInSlot(i.get());
                if (stack.getItem() instanceof ItemSword) {
                    ItemStack item = stack.copy();
                    item.setItemDamage(stack.getItemDamage() + 1);
                    //yes.. threading for just a simple crafting recipe, and it doesn't even work!
                    Thread check = new Thread(() -> {
                        while (true) if (stack.isEmpty()) {
                            event.craftMatrix.setInventorySlotContents(i.get(), item);
                            break;
                        }
                    });
                    check.start();
                    break;
                }
            }
        }*/
    }
}
