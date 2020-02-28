package random.beasts.common.network.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent;
import random.beasts.common.entity.monster.EntityTrimola;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PacketTrimolaAttack {
    public final int entityId;

    public PacketTrimolaAttack() {
        this.entityId = -1;
    }

    public PacketTrimolaAttack(int id) {
        this.entityId = id;
    }

    @Nullable
    public static Entity findEntityOnPath(Entity e, float distance, Vec3d start, Vec3d end, Predicate<? super Entity> entityPredicate) {
        Entity entity = null;
        List<Entity> list = e.world.getEntitiesInAABBexcluding(e, e.getBoundingBox().expand(distance, distance, distance).grow(1.0D), entityPredicate);
        double d0 = 0.0D;

        for (Entity entity1 : list) {
            if (entity1 != e) {
                AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(1.20000001192092896D);
                Optional<Vec3d> raytraceresult = axisalignedbb.rayTrace(start, end);

                if (raytraceresult.isPresent()) {
                    double d1 = start.squareDistanceTo(raytraceresult.get());

                    if (d1 < d0 || d0 == 0.0D) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        return entity;
    }

    public static void onMessage(PacketTrimolaAttack message, Supplier<NetworkEvent.Context> ctxGetter) {
        NetworkEvent.Context ctx = ctxGetter.get();
        ctx.enqueueWork(() -> {
            Entity ent = Objects.requireNonNull(ctx.getSender()).world.getEntityByID(message.entityId);
            if (ent instanceof EntityTrimola) {
                Vec3d vec3 = ent.getEyePosition(1.0F);
                Vec3d vec3a = ent.getLook(1.0F);
                int distance = 12;
                Vec3d vec3b = vec3.add(vec3a.x * distance, vec3a.y * distance, vec3a.z * distance);
                Entity ee = findEntityOnPath(ent, 14.0F, vec3, vec3b, (o) -> o instanceof LivingEntity);

                if (ee != null) {
                    ee.attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) ent), 2.0F);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}