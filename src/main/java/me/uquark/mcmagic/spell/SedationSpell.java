package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;

public class SedationSpell extends Spell {
    public static SedationSpell INSTANCE;

    protected SedationSpell() {
        super(new Identifier(Util.MOD_ID, "sedation_spell"));
    }

    public static SedationSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SedationSpell();
        return INSTANCE;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity == null)
            return;
        LivingEntity caster = spellEntity.getCaster();
        if (entity instanceof Angerable && caster instanceof PlayerEntity) {
            ((Angerable) entity).forgive((PlayerEntity) caster);
            return;
        }
        if (entity instanceof HostileEntity) {
            ((HostileEntity) entity).setAttacker(null);
            ((HostileEntity) entity).setTarget(null);
        }
    }
}
