package random.beasts.common.entity.passive;

import net.minecraft.world.World;

public class EntityAbyssalLegfish extends EntityLegfish {

    static {
        VARIANTS.put(0, 3);
        VARIANTS.put(1, 3);
        VARIANTS.put(2, 3);
    }

    public EntityAbyssalLegfish(World worldIn) {
        super(worldIn);
    }

    @Override
    public int getBrightnessForRender() {
        return Math.max(95, super.getBrightnessForRender());
    }

}
