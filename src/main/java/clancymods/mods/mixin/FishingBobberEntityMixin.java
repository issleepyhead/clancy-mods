package clancymods.mods.mixin;

import clancymods.mods.ClancyModsClient;
import clancymods.mods.event.PlayerEntityEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @Shadow private boolean caughtFish;

    @Inject(at = @At("TAIL"), method = "onTrackedDataSet")
    public void onTrackedDataSet(CallbackInfo ci) {
        MinecraftClient client = ClancyModsClient.getClient();
        if (caughtFish && ClancyModsClient.IS_AUTOFISH_ENABLE) {
            PlayerEntityEvent.setRecastRod(20);
            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);

        }
    }
}
