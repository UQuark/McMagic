package me.uquark.mcmagic.item;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.spell.Spell;
import me.uquark.mcmagic.voice.recognize.Recognizer;
import me.uquark.mcmagic.voice.record.Recorder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class WandItem extends RangedWeaponItem implements Recognizer.HypothesisListener {
    public static final Identifier ID = new Identifier(Util.MOD_ID, "wand");
    public static final WandItem INSTANCE = new WandItem();
    private static final Identifier CAST_PACKET_ID = new Identifier(Util.MOD_ID, "cast_packet");
    private String spellName;
    private ClientPlayerEntity user;
    private Recorder recorder;

    public WandItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1));
        Recognizer.getInstance().addHypothesisListener(this);
    }

    private static void sendCastPacket(LivingEntity user, Identifier spellId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeIdentifier(spellId);
        ClientPlayNetworking.send(CAST_PACKET_ID, buf);
    }

    private static void cast(LivingEntity user, Identifier spellId) {
        if (user == null)
            return;
        if (user.world.isClient) {
            sendCastPacket(user, spellId);
            return;
        }
        Spell s = Spell.get(spellId);
        if (s == null)
            return;
        s.cast(user);
    }

    public static void register() {
        Registry.register(Registry.ITEM, ID, INSTANCE);
        ModelPredicateProviderRegistry.register(INSTANCE, new Identifier("aiming"), (stack, world, entity, seed) -> {
            if (entity == null)
                return 0.0F;
            if (entity.getActiveItem() != stack || !(entity.getActiveItem().getItem() instanceof WandItem item))
                return 0.0F;
            if (world == null || !world.isClient)
                return 0.0F;
            return item.spellName.length() > 0 ? 1.0F : 0.0F;
        });
        ServerPlayNetworking.registerGlobalReceiver(CAST_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            Identifier spellId = buf.readIdentifier();
            cast(player, spellId);
        });
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
        if (world.isClient && recorder == null) {
            recorder = new Recorder();
            this.user = (ClientPlayerEntity) user;
            spellName = "";
            Recognizer.getInstance().start(recorder.getLine());
        }
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (world.isClient && recorder != null) {
            Recognizer.getInstance().end();
            recorder.end();
            recorder = null;
            if (spellName.length() > 0) {
                String[] spells = spellName.split(" ");
                cast(user, new Identifier(Util.MOD_ID, spells[0]));
            }
            spellName = "";
        }
    }

    @Override
    public void onHypothesis(String hypothesis, int score) {
        user.sendMessage(MutableText.of(new LiteralTextContent(hypothesis)));
        spellName = hypothesis;
    }
}
