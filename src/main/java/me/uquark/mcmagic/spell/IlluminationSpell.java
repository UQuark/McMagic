package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;

public class IlluminationSpell extends Spell {
    public static IlluminationSpell INSTANCE;

    protected IlluminationSpell() {
        super(new Identifier(Util.MOD_ID, "illumination_spell"));
    }

    public static IlluminationSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new IlluminationSpell();
        return INSTANCE;
    }

    @Override
    public void onBlockHit(SpellEntity spellEntity, BlockHitResult blockHitResult) {
        spellEntity.world.setBlockState(blockHitResult.getBlockPos().offset(blockHitResult.getSide()), Blocks.LIGHT.getDefaultState());
    }
}
