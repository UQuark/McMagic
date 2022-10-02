package me.uquark.mcmagic;

import me.uquark.mcmagic.spell.BasicSpell;
import net.fabricmc.api.ClientModInitializer;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BasicSpell.registerClient();
    }
}
