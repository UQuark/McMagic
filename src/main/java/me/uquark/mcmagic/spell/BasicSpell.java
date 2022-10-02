package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import me.uquark.mcmagic.entity.renderer.SpellEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

public class BasicSpell extends Spell {
    public static EntityType<SpellEntity<BasicSpell>> ENTITY_TYPE;
    public static BasicSpell INSTANCE;

    @Override
    public SpellEntity<BasicSpell> cast(LivingEntity caster) {
        SpellEntity<BasicSpell> entity = new SpellEntity<>(this, caster, ENTITY_TYPE);
        caster.world.spawnEntity(entity);
        return entity;
    }

    public static void register() {
        ENTITY_TYPE = SpellEntity.register(
                new Identifier(Util.MOD_ID, "basic_spell"),
                SpellEntity::new
        );
        INSTANCE = new BasicSpell();
    }

    public static void registerClient() {
        SpellEntityRenderer.register(ENTITY_TYPE, SpellEntityRenderer::new);
    }

    @Override
    public void onEntityHit(SpellEntity<? extends Spell> spellEntity, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity == null)
            return;
        Vec3d velocity = spellEntity.getVelocity().normalize();
        entity.addVelocity(velocity.x, velocity.y, velocity.z);
    }

    @Override
    public void onBlockHit(SpellEntity<? extends Spell> spellEntity, BlockHitResult blockHitResult) {

    }

    @Override
    public void onHit(SpellEntity<? extends Spell> spellEntity, LivingEntity target) {

    }
}
