package random.beasts.common.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityTrimola;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class PacketTrimolaAttack implements IMessage {
    private int entityId;

    public PacketTrimolaAttack() {
    }

    public PacketTrimolaAttack(EntityTrimola entity) {
        this.entityId = entity.getEntityId();
    }

    @Nullable
    public static Entity findEntityOnPath(Entity e, float distance, Vec3d start, Vec3d end, Predicate<? super Entity> entityPredicate) {
        Entity entity = null;
        List<Entity> list = e.world.getEntitiesInAABBexcluding(e, e.getBoundingBox().expand(distance, distance, distance).grow(1.0D), entityPredicate::test);
        double d0 = 0.0D;

        for (Entity entity1 : list) {
            if (entity1 != e) {
                AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(1.20000001192092896D);
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

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
    }

    public static class Handler implements IMessageHandler<PacketTrimolaAttack, IMessage> {
        @Override
        public IMessage onMessage(PacketTrimolaAttack message, MessageContext ctx) {
            Entity ent = BeastsMod.proxy.getPlayer(ctx).world.getEntityByID(message.entityId);
            if (ent instanceof EntityTrimola) {
                Vec3d vec3 = ent.getPositionEyes(1.0F);
                Vec3d vec3a = ent.getLook(1.0F);
                int distance = 12;
                Vec3d vec3b = vec3.addVector(vec3a.x * distance, vec3a.y * distance, vec3a.z * distance);
                Entity ee = findEntityOnPath(ent, 14.0F, vec3, vec3b, (o) -> o instanceof LivingEntity);

                if(ee != null){
                    ee.attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) ent), 2.0F);
                }
            }
            return null;
        }
    }
}