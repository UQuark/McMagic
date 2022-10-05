package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class TeleportSpell extends Spell {
    public static TeleportSpell INSTANCE;

    protected TeleportSpell() {
        super(new Identifier(Util.MOD_ID, "teleport_spell"));
    }

    public static TeleportSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TeleportSpell();
        return INSTANCE;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        spellEntity.getCaster().teleport(spellEntity.getX(), spellEntity.getY(), spellEntity.getZ(), true);
    }

    @Override
    public void onBlockHit(SpellEntity spellEntity, BlockHitResult blockHitResult) {
        spellEntity.getCaster().teleport(spellEntity.getX(), spellEntity.getY(), spellEntity.getZ(), true);
    }
}
