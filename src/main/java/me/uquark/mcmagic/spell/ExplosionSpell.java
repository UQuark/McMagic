package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.explosion.Explosion;

public class ExplosionSpell extends Spell {
    private static final float POWER = 4;
    private static ExplosionSpell INSTANCE;

    protected ExplosionSpell() {
        super(new Identifier(Util.MOD_ID, "explosion_spell"));
    }

    public static ExplosionSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ExplosionSpell();
        return INSTANCE;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        explode(spellEntity);
    }

    @Override
    public void onBlockHit(SpellEntity spellEntity, BlockHitResult blockHitResult) {
        explode(spellEntity);
    }

    private void explode(SpellEntity spellEntity) {
        spellEntity.world.createExplosion(spellEntity, spellEntity.getX(), spellEntity.getY(), spellEntity.getZ(), POWER, Explosion.DestructionType.BREAK);
    }
}
