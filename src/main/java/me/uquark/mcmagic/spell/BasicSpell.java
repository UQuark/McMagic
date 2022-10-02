package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.LivingEntity;

public class BasicSpell extends Spell {
    protected BasicSpell() {
        super(0, 0, 0);
    }

    @Override
    public SpellEntity<? extends Spell> cast(LivingEntity caster) {
        return null;
    }
}
