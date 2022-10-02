package me.uquark.mcmagic.entity.renderer;

import me.uquark.mcmagic.entity.BasicSpellEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BasicSpellEntityRenderer extends SpellEntityRenderer<BasicSpellEntity> {
    protected BasicSpellEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(BasicSpellEntity entity) {
        return null;
    }

    public static void register() {
        EntityRendererRegistry.register(BasicSpellEntity.ENTITY_TYPE, BasicSpellEntityRenderer::new);
    }
}
