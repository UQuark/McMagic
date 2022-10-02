package me.uquark.mcmagic;

import me.uquark.mcmagic.entity.BasicSpellEntity;
import me.uquark.mcmagic.item.WandItem;
import net.fabricmc.api.ModInitializer;

public class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        WandItem.register();
        BasicSpellEntity.register();
    }
}
