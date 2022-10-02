package me.uquark.mcmagic.entity;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.spell.BasicSpell;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BasicSpellEntity extends SpellEntity<BasicSpell> {
    public static final Identifier ID = new Identifier(Util.MOD_ID, "basic_spell");
    public static EntityType<BasicSpellEntity> ENTITY_TYPE;

    public BasicSpellEntity(EntityType<? extends BasicSpellEntity> entityType, World world) {
        super(entityType, world);
    }

    public BasicSpellEntity(LivingEntity caster) {
        super(caster, ENTITY_TYPE);
    }

    public static void register() {
        ENTITY_TYPE = Registry.register(
                Registry.ENTITY_TYPE,
                ID,
                FabricEntityTypeBuilder
                        .create(SpawnGroup.MISC, (EntityType<BasicSpellEntity> t, World w) -> new BasicSpellEntity(t, w))
                        .dimensions(EntityDimensions.fixed(0.1f, 0.1f))
                        .build()
        );
    }


    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Vec3d velocity = getVelocity().normalize().multiply(3);
        entityHitResult.getEntity().addVelocity(velocity.x, velocity.y, velocity.z);
        super.onEntityHit(entityHitResult);
    }
}
