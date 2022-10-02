package me.uquark.mcmagic.entity;

import me.uquark.mcmagic.spell.Spell;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class SpellEntity<T extends Spell> extends PersistentProjectileEntity {
//    public static final double SPELL_VELOCITY = 3;
    protected LivingEntity caster;
    public SpellEntity(EntityType<? extends SpellEntity> entityType, World world) {
        super(entityType, world);
        setNoGravity(true);
    }

    public SpellEntity(LivingEntity caster, EntityType<? extends SpellEntity> entityType) {
        this(entityType, caster.world);
        this.caster = caster;
        Vec3d launchPos = new Vec3d(caster.getX(), caster.getEyeY() - (double)0.1f, caster.getZ());
        launchPos.add(caster.getVelocity().multiply(2));
        setPosition(launchPos);
        setRotation(caster.getHeadYaw(), caster.getPitch());
        setVelocity(caster.getRotationVector().multiply(3));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void tick() {
        super.tick();
        if (getY() > world.getTopY() || getY() < world.getBottomY())
            discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        discard();
    }

    @Override
    protected void onHit(LivingEntity target) {
        discard();
    }

    @Override
    protected float getDragInWater() {
        return 0;
    }
}
