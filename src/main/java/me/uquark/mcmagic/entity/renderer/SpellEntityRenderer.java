package me.uquark.mcmagic.entity.renderer;

import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

public abstract class SpellEntityRenderer<T extends SpellEntity<?>> extends EntityRenderer<T> {
    protected SpellEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
}
