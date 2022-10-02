package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.LivingEntity;

public abstract class Spell {
    public final double x, y, z;

    protected Spell(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getCost() {
        return Math.sqrt(x*x+y*y+z*z);
    }

    public abstract SpellEntity<? extends Spell> cast(LivingEntity caster);
}
