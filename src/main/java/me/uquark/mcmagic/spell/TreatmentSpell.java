package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;

public class TreatmentSpell extends Spell {
    private static final float POWER = 7;
    public static TreatmentSpell INSTANCE;

    protected TreatmentSpell() {
        super(new Identifier(Util.MOD_ID, "treatment_spell"));
    }

    public static TreatmentSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TreatmentSpell();
        return INSTANCE;
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
        if (entity == null)
            return;
        if (!(entity instanceof LivingEntity))
            return;
        ((LivingEntity) entity).heal(POWER);
    }
}
