package net.msrandom.beasts.client.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.common.BeastsMod;
import net.msrandom.beasts.common.init.BeastsBlocks;

@Mod.EventBusSubscriber(value = {Side.CLIENT}, modid = BeastsReference.ID)
public class ClientEvents {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void tick(TickEvent.RenderTickEvent event) {
        BeastsBlocks.PALM_LEAVES.setGraphicsLevel(BeastsMod.proxy.isFancyGraphics());
    }
}
