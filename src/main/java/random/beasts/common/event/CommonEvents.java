package random.beasts.common.event;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Tuple;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import random.beasts.api.entity.BeastsBranchie;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityPufferfishDog;
import random.beasts.common.init.BeastsLootTables;
import random.beasts.common.network.BeastsPacketHandler;
import random.beasts.common.world.storage.loot.BeastsLootTable;

import java.util.Collection;
import java.util.function.Function;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsMod.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void serverStarting(FMLServerStartingEvent event) {
        BeastsPacketHandler.initPackets();
    }

    @SubscribeEvent
    public static void addEntity(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof CatEntity) {
            final CatEntity ocelot = (CatEntity) event.getEntity();
            if (ocelot != null) {
                ocelot.goalSelector.addGoal(1, new NonTamedTargetGoal<>(ocelot, EntityPufferfishDog.class, false, target -> target != null && target.getDistance(ocelot) < 32.0));
                //ocelot.goalSelector.addGoal(1, new EntityAITargetNonTamed<>(ocelot, EntityRabbitman.class, false, target -> target != null && target.getDistance(ocelot) < 32.0));
            }
        }/* else if (event.getEntity() instanceof WolfEntity) {
            final WolfEntity wolf = (WolfEntity) event.getEntity();
            if (wolf != null)
                wolf.goalSelector.addGoal(1, new NonTamedTargetGoal<>(wolf, EntityRabbitman.class, false, target -> target != null && target.getDistance(wolf) < 32.0));
        }*/
    }

    /*@SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player.world.isRemote)
            return;

        Vec3d vec3 = player.getPositionEyes(1.0F);
        Vec3d vec3a = player.getLook(1.0F);
        int distance = 14;
        Vec3d vec3b = vec3.addVector(vec3a.x * distance, vec3a.y * distance, vec3a.z * distance);

        Entity ee = null;
        List<Entity> list = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(distance + 1));
        double d0 = 0.0D;
        for (Entity entity1 : list) {
            if (entity1 != player) {
                AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(0.30000001192092896D);
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
        if(ee instanceof EntityPufferfishDog) BeastsTriggers.DISCOVER_PUFFERFISH_DOG.trigger((ServerPlayerEntity) player);
    }*/

    @SubscribeEvent
    public static void loadLootTable(LootTableLoadEvent event) {
        BeastsLootTable table = BeastsLootTables.TABLES.get(event.getName());
        if (table != null) {
            LootPool pool = event.getTable().getPool("main");
            if (pool != null) pool.addEntry(table.tableSupplier.get());
        }
    }

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        Tuple<Integer, Function<BlockEvent.BreakEvent, ? extends BeastsBranchie>> createFunc = null;
        BlockState state = event.getState();
        Block block = state.getBlock();
        for (Collection<? extends Block> blocks : BeastsBranchie.TYPES.keySet()) {
            if (blocks.contains(block)) {
                createFunc = BeastsBranchie.TYPES.get(blocks);
                break;
            }
        }
        if (createFunc != null && !event.getWorld().isRemote() && event.getPlayer().getRNG().nextInt(createFunc.getA()) == 0 && event.getWorld().getBlockState(event.getPos().down()).getBlock() != block) {
            BeastsBranchie entity = createFunc.getB().apply(event);
            if (entity != null) {
                entity.scream();
                entity.setAttackTarget(event.getPlayer());
                event.getWorld().addEntity(entity);
            }
        }
    }

    @SubscribeEvent
    public static void attackLiving(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (!player.getActiveItemStack().isEmpty()) {
                ItemStack stack = player.getActiveItemStack();
                float damage = event.getAmount();
                if (damage > 5.0F && (stack.getItem() instanceof ShieldItem && stack.getItem() != Items.SHIELD)) {
                    int i = Math.min(1 + (int) damage, stack.getMaxDamage() - stack.getDamage());
                    stack.damageItem(i, player, p -> {
                        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 0.9f, 0.8F + player.world.rand.nextFloat() * 0.4F);
                    });
                }
            }
        }
    }

    /*@SubscribeEvent
    public static void craftItem(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem() == BeastsItems.COCONUT_JUICE) {
            for (AtomicInteger i = new AtomicInteger(); i.get() < event.craftMatrix.getSizeInventory(); i.getAndIncrement()) {
                ItemStack stack = event.craftMatrix.getStackInSlot(i.get());
                if (stack.getItem() instanceof SwordItem) {
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
        }
    }*/
}
