package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import me.uquark.mcmagic.entity.renderer.SpellEntityRenderer;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IgnitionSpell extends Spell {
    public static final Identifier ID = new Identifier(Util.MOD_ID, "ignition_spell");
    public static EntityType<SpellEntity> ENTITY_TYPE;
    public static IgnitionSpell INSTANCE;

    public static void register() {
        ENTITY_TYPE = SpellEntity.register(
                ID,
                SpellEntity::new
        );
        INSTANCE = new IgnitionSpell();
        Spell.register(ID, INSTANCE);
    }

    public static void registerClient() {
        SpellEntityRenderer.register(ENTITY_TYPE, SpellEntityRenderer::new);
    }

    @Override
    public SpellEntity cast(LivingEntity caster) {
        SpellEntity entity = new SpellEntity(this, caster, ENTITY_TYPE);
        caster.world.spawnEntity(entity);
        return entity;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() != null)
            entityHitResult.getEntity().setOnFireFor(10);
    }

    @Override
    public void onBlockHit(SpellEntity spellEntity, BlockHitResult blockHitResult) {
        World world = spellEntity.world;
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) {
            world.setBlockState(blockPos, blockState.with(Properties.LIT, true), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            return;
        }
        BlockPos blockPos2 = blockPos.offset(blockHitResult.getSide());
        if (AbstractFireBlock.canPlaceAt(world, blockPos2, spellEntity.getMovementDirection())) {
            BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
            world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
        }
    }

    @Override
    public void onHit(SpellEntity spellEntity, LivingEntity target) {

    }

    @Override
    public Identifier getId() {
        return ID;
    }
}
