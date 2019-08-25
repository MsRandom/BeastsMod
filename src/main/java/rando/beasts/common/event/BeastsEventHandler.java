package rando.beasts.common.event;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rando.beasts.common.advancements.BeastsTriggers;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.utils.BeastsReference;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class BeastsEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = e.player;
        if (player.world.isRemote)
            return;

        Vec3d vec3 = player.getPositionEyes(1.0F);
        Vec3d vec3a = player.getLook(1.0F);
        int distance = 14;
        Vec3d vec3b = vec3.addVector(vec3a.x * distance, vec3a.y * distance, vec3a.z * distance);

        Entity ee = findEntityOnPath(player, 14.0F, vec3, vec3b, o ->{
                return true;
            }
        );

        if(ee == null)
            return;

        if(ee instanceof EntityPufferfishDog){
            BeastsTriggers.DISCOVER_PUFFERFISH_DOG.trigger((EntityPlayerMP) player);
        }

    }

    @Nullable
    public static Entity findEntityOnPath(EntityPlayer player, float distance, Vec3d start, Vec3d end, Predicate entityPredicate) {
        Entity entity = null;
        List<Entity> list = player.world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().expand((double)distance, (double)distance, (double)distance).grow(1.0D), entityPredicate);
        double d0 = 0.0D;
        for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity)list.get(i);
            if (entity1 != player) {
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);
                if (raytraceresult != null) {
                    double d1 = start.squareDistanceTo(raytraceresult.hitVec);
                    if (d1 < d0 || d0 == 0.0D) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }
        return entity;
    }
}
