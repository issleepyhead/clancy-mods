package clancymods.mods.event;

import clancymods.mods.ClancyModsClient;
import clancymods.mods.network.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;


public abstract class PlayerEntityEvent {

    public  static int floatingTicks = 0;
    public  static double oldY = 0.0D;
    private static int recastRod = -1;
    private static int tickDistance = 0;
    private  static boolean isTeleporting = false;
    private  static Vec3d fromPos = null;
    private  static Vec3d toPos = null;

    public static void tick(MinecraftClient client) {
        if (client != null && client.player != null) {


            if(ClancyModsClient.nightVision.wasPressed()) {
                if (!client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                    client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, StatusEffectInstance.INFINITE));
                    return;
                } else {
                    client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                }
                return;
            }


            if (recastRod > 0)
                --recastRod;

            if (recastRod == 0) {
                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                recastRod = -1;
            }

            if (!client.player.getAbilities().flying && !client.player.isOnGround()) {
                client.player.networkHandler.getConnection().send((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(client.player.getX(),
                        client.player.getY(),
                        client.player.getZ(), true), null);
                return;
            }

            if (client.player.getY() >= oldY - 0.0433D)
                ++floatingTicks;

            // My computer is slow, so we use 3 ticks to keep the updated position in sync with the server
            oldY = client.player.getY();
            if (client.player.getAbilities().flying && floatingTicks > 3 && ClancyModsClient.IS_FLY_ENABLE) {
                PacketSender.sendFallPacket(client.player.getPos().subtract(0, 0.0433D, 0));
                floatingTicks = 0;
            }
        }
    }

     public static void setRecastRod(int countdown) {
        recastRod = countdown;
    }

    public static boolean myBlock(Block block) {
        List<Block> BLOCKS = Arrays.asList(Blocks.COAL_ORE, Blocks.DIAMOND_ORE, Blocks.COPPER_ORE,
                Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.REDSTONE_ORE, Blocks.LAVA, Blocks.LAPIS_ORE,
                Blocks.NETHER_QUARTZ_ORE, Blocks.NETHER_GOLD_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.DEEPSLATE_IRON_ORE, Blocks.EMERALD_ORE);
        return BLOCKS.contains(block);
    }

    static void teleportFromTo(MinecraftClient client, Vec3d fromPos, Vec3d toPos) {
        double distanceBlink = 8.5;
        double targetDistance = Math.ceil(fromPos.distanceTo(toPos)/distanceBlink);
        for(int i = 1; i <= targetDistance; i++) {
            Vec3d tempPos = fromPos.lerp(toPos, i /targetDistance);
            PacketSender.sendFallPacket(tempPos);
            if(i % 4 == 0) {
                try {
                    Thread.sleep((long)(1/20) * 1000);

                } catch (InterruptedException e) {

                }
            }
        }
    }

}
