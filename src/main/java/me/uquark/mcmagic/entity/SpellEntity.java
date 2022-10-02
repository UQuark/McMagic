package me.uquark.mcmagic.entity;

import me.uquark.mcmagic.spell.Spell;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SpellEntity<T extends Spell> extends PersistentProjectileEntity {
    public static final double SPELL_VELOCITY = 1;
    public T spell;

    protected LivingEntity caster;
    public SpellEntity(EntityType<? extends SpellEntity<T>> entityType, World world) {
        super(entityType, world);
        setNoGravity(true);
    }

    public SpellEntity(T spell, LivingEntity caster, EntityType<? extends SpellEntity<T>> entityType) {
        this(entityType, caster.world);

        final double SAFETY_MARGIN = 2;

        this.caster = caster;
        this.spell = spell;

        Vec3d launchPos = new Vec3d(caster.getX(), caster.getEyeY() - (double)0.1f, caster.getZ());
        launchPos.add(caster.getVelocity().multiply(SAFETY_MARGIN));

        setPosition(launchPos);
        setRotation(caster.getHeadYaw(), caster.getPitch());
        setVelocity(caster.getRotationVector().multiply(SPELL_VELOCITY));
    }

    @Override
    public void tick() {
        super.tick();
        if (getY() > world.getTopY() || getY() < world.getBottomY())
            discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!world.isClient)
            spell.onEntityHit(this, entityHitResult);
        discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!world.isClient)
            spell.onBlockHit(this, blockHitResult);
        discard();
    }

    @Override
    protected void onHit(LivingEntity target) {
        if (!world.isClient)
            spell.onHit(this, target);
        discard();
    }

    @Override
    protected float getDragInWater() {
        return 1;
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    public static <T extends SpellEntity<?>> EntityType<T> register(Identifier id, EntityType.EntityFactory<T> factory) {
        return Registry.register(
                Registry.ENTITY_TYPE,
                id,
                FabricEntityTypeBuilder
                        .create(SpawnGroup.MISC, factory)
                        .dimensions(EntityDimensions.fixed(0.1f, 0.1f))
                        .build()
        );
    }
}
