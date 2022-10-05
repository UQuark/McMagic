package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

public class PullSpell extends Spell {
    private static final double VELOCITY = 3;
    public static PullSpell INSTANCE;

    protected PullSpell() {
        super(new Identifier(Util.MOD_ID, "pull_spell"));
    }

    public static PullSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PullSpell();
        return INSTANCE;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity == null)
            return;
        Vec3d velocity = spellEntity.getVelocity().normalize().multiply(-VELOCITY);
        entity.addVelocity(velocity.x, velocity.y, velocity.z);
    }
}
