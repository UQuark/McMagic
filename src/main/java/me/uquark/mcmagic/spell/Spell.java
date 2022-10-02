package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public abstract class Spell {
    public abstract SpellEntity<? extends Spell> cast(LivingEntity caster);

    public abstract void onEntityHit(SpellEntity<? extends Spell> spellEntity, EntityHitResult entityHitResult);
    public abstract void onBlockHit(SpellEntity<? extends Spell> spellEntity, BlockHitResult blockHitResult);
    public abstract void onHit(SpellEntity<? extends Spell> spellEntity, LivingEntity target);
}
