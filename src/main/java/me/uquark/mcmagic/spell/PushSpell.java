package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

public class PushSpell extends Spell {
    private static final double VELOCITY = 3;

    private static PushSpell INSTANCE;

    protected PushSpell() {
        super(new Identifier(Util.MOD_ID, "push_spell"));
    }

    public static PushSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PushSpell();
        return INSTANCE;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity == null)
            return;
        Vec3d velocity = spellEntity.getVelocity().normalize().multiply(VELOCITY);
        entity.addVelocity(velocity.x, velocity.y, velocity.z);
    }
}
