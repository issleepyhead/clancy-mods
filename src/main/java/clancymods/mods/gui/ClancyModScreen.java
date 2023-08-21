package clancymods.mods.gui;


import clancymods.mods.ClancyModsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class ClancyModScreen extends Screen {

    public static int blocks = 0;
    private Screen parent;
    public ClancyModScreen(Screen parent) {
        super(Text.literal("Clancy Mods"));
        this.parent = parent;
    }
    private Text autoFishText() {
        return Text.literal((ClancyModsClient.IS_AUTOFISH_ENABLE)? "Auto Fish Enable": "Auto Fish Disable");
    }
    private Text flyText() {
        return Text.literal((ClancyModsClient.IS_FLY_ENABLE) ? "Flying Enable" : "Flying Disable");
    }

    private Text flySpeedText() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        return Text.literal(String.format("%1.2f", player.getAbilities().getFlySpeed()));
    }

    private Text teleportText() {
        return Text.literal(String.format("%d", blocks));
    }
    private Text xRayText() {return Text.literal(ClancyModsClient.IS_EXRAY_ENABLE ? "Xray Enable" : "Xray Disable");}


    @Override
    public void init() {
        ButtonWidget flySpeedTextWidget = addDrawableChild(new ButtonWidget.Builder(flySpeedText(),
                button -> {
                    button.setMessage(flySpeedText());
                })
                .dimensions( this.width / 2 - 100, 40, 50, 20)
                .build());
        addDrawableChild(new ButtonWidget.Builder(Text.literal("+"),
                button -> {
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    player.getAbilities().setFlySpeed(player.getAbilities().getFlySpeed() + .01f);
                    flySpeedTextWidget.onPress();
                })
                .dimensions(this.width / 2 - 100 + 55, 40, 20, 20)
                .build());
        addDrawableChild(new ButtonWidget.Builder(Text.literal("-"),
                button -> {
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    player.getAbilities().setFlySpeed(player.getAbilities().getFlySpeed() - .01f);
                    flySpeedTextWidget.onPress();
                })
                .dimensions(this.width / 2 - 100 + 80, 40, 20, 20)
                .build());


        addDrawableChild(new ButtonWidget.Builder(autoFishText(),
                button -> {
                    ClancyModsClient.IS_AUTOFISH_ENABLE = !ClancyModsClient.IS_AUTOFISH_ENABLE;
                    button.setMessage(autoFishText());
                })
                .dimensions(this.width / 2 - 100, 40 + 30, 100 * 2, 20)
                .build());
        addDrawableChild(new ButtonWidget.Builder(flyText(),
                button -> {
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    ClancyModsClient.IS_FLY_ENABLE = !ClancyModsClient.IS_FLY_ENABLE;
                    player.getAbilities().allowFlying = ClancyModsClient.IS_FLY_ENABLE;
                    button.setMessage(flyText());
                })
                .dimensions(this.width / 2 - 100, 70 + 30, 100 * 2, 20)
                .build());


//        ButtonWidget teleportBtn = addDrawable(new ButtonWidget.Builder(teleportText(),
//                button -> {
//                    button.setMessage(teleportText());
//                })
//                .dimensions(this.width / 2 - 100, 130, 50, 20)
//                .build());
//        addDrawableChild(new ButtonWidget.Builder(Text.literal("+"),
//                button -> {
//                    ++blocks;
//                    teleportBtn.onPress();
//                })
//                .dimensions(this.width / 2 - 100 + 55, 130, 20, 20)
//                .build());
//        addDrawableChild(new ButtonWidget.Builder(Text.literal("-"),
//                button -> {
//                    --blocks;
//                    teleportBtn.onPress();
//                })
//                .dimensions(this.width / 2 - 100 + 80, 130, 20, 20)
//                .build());
        addDrawableChild(new ButtonWidget.Builder(xRayText(),
                button -> {
                    ClancyModsClient.IS_EXRAY_ENABLE = !ClancyModsClient.IS_EXRAY_ENABLE;
                    MinecraftClient client = ClancyModsClient.getClient();
                    client.worldRenderer.reload();
                    client.worldRenderer.drawEntityOutlinesFramebuffer();

                    button.setMessage(xRayText());
                })
                .dimensions(this.width / 2 - 100, 130, 100 * 2, 20)
                .build());

        addDrawableChild(new ButtonWidget.Builder(Text.literal("Back"),
                button -> client.setScreen(parent))
                .dimensions(this.width / 2 - 100, 160, 100 * 2, 20)
                .build());
    }
}
