package me.uquark.mcmagic.spell;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.entity.SpellEntity;
import net.minecraft.block.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IgnitionSpell extends Spell {
    private static final int FIRE_DURATION = 10;
    private static IgnitionSpell INSTANCE;

    protected IgnitionSpell() {
        super(new Identifier(Util.MOD_ID, "ignition_spell"));
    }

    public static IgnitionSpell getInstance() {
        if (INSTANCE == null)
            INSTANCE = new IgnitionSpell();
        return INSTANCE;
    }

    @Override
    public void onEntityHit(SpellEntity spellEntity, EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() != null)
            entityHitResult.getEntity().setOnFireFor(FIRE_DURATION);
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
}
