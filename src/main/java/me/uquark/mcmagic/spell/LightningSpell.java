package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class LightningSpell extends Spell {
    public static LightningSpell INSTANCE;

    protected LightningSpell() {
        super(new Identifier(Util.MOD_ID, "lightning_spell"));
    }

    public static LightningSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LightningSpell();
        return INSTANCE;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (!(spellEntity.world instanceof ServerWorld) || entity == null)
            return;
        EntityType.LIGHTNING_BOLT.spawn(
                (ServerWorld) spellEntity.world,
                null,
                null,
                null,
                entity.getBlockPos(),
                SpawnReason.TRIGGERED,
                true,
                false
        );
    }

    @Override
    public void onBlockHit(SpellEntity spellEntity, BlockHitResult blockHitResult) {
        if (!(spellEntity.world instanceof ServerWorld))
            return;
        EntityType.LIGHTNING_BOLT.spawn(
                (ServerWorld) spellEntity.world,
                null,
                null,
                null,
                spellEntity.getBlockPos(),
                SpawnReason.TRIGGERED,
                true,
                false
        );
    }
}
