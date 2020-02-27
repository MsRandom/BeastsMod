package random.beasts.common.entity.passive;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public class EntityTupala extends AnimalEntity {

    public EntityTupala(EntityType<? extends EntityTupala> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
    }

}
