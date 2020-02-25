package random.beasts.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class BeastsToolSet {

    public final AxeItem axe;
    public final PickaxeItem pickaxe;
    public final ShovelItem shovel;
    public final SwordItem sword;
    public final HoeItem hoe;

    public BeastsToolSet(IItemTier material, String name) {
        this.axe = new BeastsAxe(material, name + "_axe", this);
        this.pickaxe = new BeastsPickaxe(material, name + "_pickaxe", this);
        this.shovel = new BeastsShovel(material, name + "_shovel", this);
        this.sword = new BeastsSword(material, name + "_sword", this);
        this.hoe = new BeastsHoe(material, name + "_hoe", this);
    }

    protected boolean damageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return false;
    }
}
