package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import me.uquark.mcmagic.entity.renderer.SpellEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class LightningSpell extends Spell {
    public static final Identifier ID = new Identifier(Util.MOD_ID, "lightning_spell");
    public static EntityType<SpellEntity> ENTITY_TYPE;
    public static LightningSpell INSTANCE;

    public static void register() {
        ENTITY_TYPE = SpellEntity.register(
                ID,
                SpellEntity::new
        );
        INSTANCE = new LightningSpell();
        Spell.register(ID, INSTANCE);
    }

    public static void registerClient() {
        SpellEntityRenderer.register(ENTITY_TYPE, SpellEntityRenderer::new);
    }

    @Override
    public SpellEntity cast(LivingEntity caster) {
        SpellEntity entity = new SpellEntity(this, caster, ENTITY_TYPE);
        caster.world.spawnEntity(entity);
        return entity;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (!(spellEntity.world instanceof ServerWorld) || entity == null)
            return;
        EntityType.LIGHTNING_BOLT.spawn(
                (ServerWorld) spellEntity.world,
                null,
                null,
                null,
                entity.getBlockPos(),
                SpawnReason.TRIGGERED,
                true,
                false
        );
    }

    @Override
    public void onBlockHit(SpellEntity spellEntity, BlockHitResult blockHitResult) {
        if (!(spellEntity.world instanceof ServerWorld))
            return;
        EntityType.LIGHTNING_BOLT.spawn(
                (ServerWorld) spellEntity.world,
                null,
                null,
                null,
                spellEntity.getBlockPos(),
                SpawnReason.TRIGGERED,
                true,
                false
        );
    }

    @Override
    public void onHit(SpellEntity spellEntity, LivingEntity target) {

    }

    @Override
    public Identifier getId() {
        return ID;
    }
}
