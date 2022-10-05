package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.entity.SpellEntity;
import me.uquark.mcmagic.entity.renderer.SpellEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

import java.util.HashMap;

public abstract class Spell {
    private static final HashMap<Identifier, Spell> spells = new HashMap<>();

    public final EntityType<SpellEntity> ENTITY_TYPE;
    public final Identifier ID;

    protected Spell(Identifier id) {
        ID = id;
        spells.put(id, this);
        ENTITY_TYPE = SpellEntity.register(
                id,
                SpellEntity::new
        );
        try {
            SpellEntityRenderer.register(ENTITY_TYPE, SpellEntityRenderer::new);
        } catch (Exception ignored) {
        }
    }

    public static Spell get(Identifier id) {
        return spells.get(id);
    }

    public SpellEntity cast(LivingEntity caster) {
        SpellEntity entity = new SpellEntity(this, caster, ENTITY_TYPE);
        caster.world.spawnEntity(entity);
        return entity;
    }

    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
    }

    public void onBlockHit(SpellEntity spellEntity, BlockHitResult blockHitResult) {
    }

    public void onHit(SpellEntity spellEntity, LivingEntity target) {
    }
}
