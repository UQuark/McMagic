package me.uquark.mcmagic.entity.renderer;

import me.uquark.mcmagic.entity.SpellEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class SpellEntityRenderer<T extends SpellEntity> extends EntityRenderer<T> {
    public SpellEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }

    public static <T extends SpellEntity> void register(EntityType<T> entityType, EntityRendererFactory<T> factory) {
        EntityRendererRegistry.register(entityType, factory);
    }
}
