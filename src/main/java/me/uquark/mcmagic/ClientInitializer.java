package me.uquark.mcmagic;

import me.uquark.mcmagic.entity.renderer.BasicSpellEntityRenderer;
import net.fabricmc.api.ClientModInitializer;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BasicSpellEntityRenderer.register();
    }
}
