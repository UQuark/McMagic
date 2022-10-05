package me.uquark.mcmagic;

import me.uquark.mcmagic.item.WandItem;
import me.uquark.mcmagic.spell.*;
import net.fabricmc.api.ModInitializer;

public class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        WandItem.register();

        IgnitionSpell.register();
        PushSpell.register();
        PullSpell.register();
        ExplosionSpell.register();
        HarmSpell.register();
        TeleportSpell.register();
        LightningSpell.register();
        SedationSpell.register();
        TreatmentSpell.register();
        IlluminationSpell.register();
    }
}
