package rando.beasts.common.utils.handlers;

import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rando.beasts.common.utils.BeastsReference;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.entity.passive.EntityRabbitman;

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
}
