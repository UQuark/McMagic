package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

import java.util.HashMap;

public abstract class Spell {
    private static final HashMap<Identifier, Spell> spells = new HashMap<>();
    public static void register(Identifier id, Spell instance) {
        spells.put(id, instance);
    }
    public static Spell get(Identifier id) {
        return spells.get(id);
    }

    public abstract SpellEntity cast(LivingEntity caster);

    public abstract void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult);
    public abstract void onBlockHit(SpellEntity spellEntity, BlockHitResult blockHitResult);
    public abstract void onHit(SpellEntity spellEntity, LivingEntity target);
    public abstract Identifier getId();
}
