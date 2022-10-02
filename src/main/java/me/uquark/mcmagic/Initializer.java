package me.uquark.mcmagic;

import me.uquark.mcmagic.item.WandItem;
import me.uquark.mcmagic.spell.BasicSpell;
import net.fabricmc.api.ModInitializer;

public class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        BasicSpell.register();
        WandItem.register();
    }
}
