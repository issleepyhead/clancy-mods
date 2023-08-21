package clancymods.mods.network;

import clancymods.mods.ClancyModsClient;
import clancymods.mods.mixin.ClientConnectionMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class PacketSender {

    public static void sendFallPacket(Vec3d pos) {
        MinecraftClient client = ClancyModsClient.getClient();
        ClientConnectionMixin conn = (ClientConnectionMixin) client.player.networkHandler.getConnection();

        Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), false);
        conn.sendIm(packet, null);
    }



}
