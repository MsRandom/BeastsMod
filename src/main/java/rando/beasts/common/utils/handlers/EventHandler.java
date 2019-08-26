package rando.beasts.common.utils.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rando.beasts.common.init.BeastsTriggers;
import rando.beasts.common.utils.BeastsReference;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.entity.passive.EntityRabbitman;

import java.util.List;

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
    public static void craftItem(CraftingEvent event) {
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
}
