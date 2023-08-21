package clancymods.mods.mixin;

import clancymods.mods.ClancyModsClient;
import clancymods.mods.event.PlayerEntityEvent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class  AbstractBlockMixin {

    @Inject(at = @At("HEAD"), method = "isSideInvisible", cancellable = true)
    public void isSideInvisible(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (ClancyModsClient.IS_EXRAY_ENABLE){
            if (PlayerEntityEvent.myBlock(state.getBlock())) {
                cir.setReturnValue(false);
                return;
            }
            cir.setReturnValue(true);
            return;
        }
    }
}
