package clancymods.mods.mixin;


import clancymods.mods.ClancyModsClient;
import clancymods.mods.event.PlayerEntityEvent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {


    @Shadow public abstract Block getBlock();
    @Inject(at = @At("HEAD"), method = "isSideInvisible", cancellable = true)
    public void isSideInvisible(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (ClancyModsClient.IS_EXRAY_ENABLE){
            if (PlayerEntityEvent.myBlock(state.getBlock())) {
                cir.setReturnValue(false);
                return;
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "getAmbientOcclusionLightLevel", cancellable = true)
    public void getAmbientOcclusionLightLevel(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (ClancyModsClient.IS_EXRAY_ENABLE ){
            if(PlayerEntityEvent.myBlock(world.getBlockState(pos).getBlock())) {
                cir.setReturnValue(1.0F);
                return;
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "getCullingFace", cancellable = true)
    public void getCullingFace(BlockView world, BlockPos pos, Direction direction, CallbackInfoReturnable<VoxelShape> cir) {
        if (ClancyModsClient.IS_EXRAY_ENABLE) {
            if (PlayerEntityEvent.myBlock(world.getBlockState(pos).getBlock())) {
                cir.setReturnValue(VoxelShapes.fullCube());
                return;
            }
            cir.setReturnValue(VoxelShapes.empty());
            return;
        }
    }


    @Inject(at = @At("HEAD"), method =  "getLuminance", cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> cir) {
        if (ClancyModsClient.IS_EXRAY_ENABLE) {
            if(PlayerEntityEvent.myBlock(this.getBlock())) {
                cir.setReturnValue(12);
                return;
            }
        }
    }

}
