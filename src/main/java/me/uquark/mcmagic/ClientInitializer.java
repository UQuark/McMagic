package me.uquark.mcmagic;

import me.uquark.mcmagic.spell.BasicSpell;
import me.uquark.mcmagic.voice.recognize.Recognizer;
import me.uquark.mcmagic.voice.recognize.ps4j.PS4J;
import net.fabricmc.api.ClientModInitializer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        try {
            Recognizer.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BasicSpell.registerClient();
    }
}
