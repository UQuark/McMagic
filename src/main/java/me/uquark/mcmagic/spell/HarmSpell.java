package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;

public class HarmSpell extends Spell {
    private static final float POWER = 7;
    public static HarmSpell INSTANCE;

    protected HarmSpell() {
        super(new Identifier(Util.MOD_ID, "harm_spell"));
    }

    public static HarmSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HarmSpell();
        return INSTANCE;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity == null)
            return;
        entity.damage(DamageSource.MAGIC, POWER);
    }
}
