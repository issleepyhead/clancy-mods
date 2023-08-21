package clancymods.mods;

import clancymods.mods.event.PlayerEntityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;

public class ClancyModsClient implements ClientModInitializer, HudRenderCallback {

    public static String FORMAT_STRING = "";
    public static boolean IS_FLY_ENABLE = false;
    public static boolean IS_AUTOFISH_ENABLE = false;
    public static boolean IS_EXRAY_ENABLE = false;
    public static MinecraftClient client;
    private static ClancyModsClient instance;
    public static KeyBinding nightVision = KeyBindingHelper.registerKeyBinding(new KeyBinding("Night Vision", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_O, "Clancy Mods"));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(PlayerEntityEvent::tick);
        client = MinecraftClient.getInstance();
        HudRenderCallback.EVENT.register(this::onHudRender);
        instance = new ClancyModsClient();
    }
    public static MinecraftClient getClient() {
        return (client != null) ? client : null;
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int padding = 4;
        Vec3d vec = client.player.getPos();
        FORMAT_STRING = String.format("%s XYZ: %1.2f / %1.2f / %1.2f", client.player.getName().getString(), vec.getX(), vec.getY(), vec.getZ());
        drawContext.drawText(client.textRenderer, FORMAT_STRING, 10, 10, 0xffffff, false);
    }

    public static ClancyModsClient getInstance() {
        if (instance == null) instance = new ClancyModsClient();
        return  instance;
    }
}
