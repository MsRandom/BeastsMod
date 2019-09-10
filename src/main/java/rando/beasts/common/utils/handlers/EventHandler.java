package rando.beasts.common.utils.handlers;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rando.beasts.common.entity.monster.EntityBranchieBase;
import rando.beasts.common.entity.monster.EntityCoralBranchie;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.entity.passive.EntityRabbitman;
import rando.beasts.common.init.BeastsTriggers;
import rando.beasts.common.utils.BeastsReference;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class EventHandler {

    @SubscribeEvent
    public static void entityJoin(EntityJoinWorldEvent event) {
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

    @SubscribeEvent
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
    }
    
    @SubscribeEvent
    public static void lootTable(LootTableLoadEvent event) {
    	if(event.getName().toString().equals("minecraft:gameplay/fishing")) {
    		event.getTable().getPool("main").addEntry(LootHandler.getInjectEntry("fish", 100));
    	}
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
}
