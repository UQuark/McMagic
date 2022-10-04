package me.uquark.mcmagic.item;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.voice.recognize.Recognizer;
import me.uquark.mcmagic.voice.record.Recorder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class WandItem extends RangedWeaponItem {
    public static final Identifier ID = new Identifier(Util.MOD_ID, "wand");
    public static final WandItem INSTANCE = new WandItem();

    public WandItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1));
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return s -> false;
    }

    @Override
    public int getRange() {
        return 0;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            Recognizer.getInstance().start(Recorder.getInstance().getLine());
        }
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (world.isClient) {
            Recognizer.getInstance().end();
        }
    }

    public static void register() {
        Registry.register(Registry.ITEM, ID, INSTANCE);
        ModelPredicateProviderRegistry.register(INSTANCE, new Identifier("aiming"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            }
            return entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F;
        });
    }
}
