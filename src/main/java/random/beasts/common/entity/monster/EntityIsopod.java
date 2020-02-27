package random.beasts.common.entity.monster;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public class EntityIsopod extends AnimalEntity {

    public EntityIsopod(EntityType<? extends EntityIsopod> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8d);
    }

}
