package me.uquark.mcmagic;

import me.uquark.mcmagic.voice.recognize.Recognizer;
import net.fabricmc.api.ClientModInitializer;

import java.io.IOException;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        try {
            Recognizer.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
