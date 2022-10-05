package me.uquark.mcmagic;

import me.uquark.mcmagic.item.WandItem;
import me.uquark.mcmagic.spell.*;
import net.fabricmc.api.ModInitializer;

public class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        WandItem.register();

        IgnitionSpell.getInstance();
        PushSpell.getInstance();
        PullSpell.getInstance();
        ExplosionSpell.getInstance();
        HarmSpell.getInstance();
        TeleportSpell.getInstance();
        LightningSpell.getInstance();
        SedationSpell.getInstance();
        TreatmentSpell.getInstance();
        IlluminationSpell.getInstance();
    }
}
